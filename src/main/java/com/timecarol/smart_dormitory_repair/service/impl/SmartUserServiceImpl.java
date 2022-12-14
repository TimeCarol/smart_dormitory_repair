package com.timecarol.smart_dormitory_repair.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.timecarol.smart_dormitory_repair.dto.response.SmartUserDto;
import com.timecarol.smart_dormitory_repair.entity.SmartRole;
import com.timecarol.smart_dormitory_repair.entity.SmartUser;
import com.timecarol.smart_dormitory_repair.exception.BusinessException;
import com.timecarol.smart_dormitory_repair.mapper.SmartUserMapper;
import com.timecarol.smart_dormitory_repair.service.ISmartRoleService;
import com.timecarol.smart_dormitory_repair.service.ISmartUserService;
import com.timecarol.smart_dormitory_repair.vo.SmartUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author timecarol
 * @since 2022-09-03
 */
@Slf4j
@Service
@Transactional
public class SmartUserServiceImpl extends ServiceImpl<SmartUserMapper, SmartUser> implements ISmartUserService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder; //密码加密

    @Autowired
    ISmartRoleService roleService;

    @Override
    public SmartUserDto query(SmartUserVo vo) {
        SmartUserDto query = baseMapper.query(vo);
        if (Objects.nonNull(query) && Objects.nonNull(query.getRoleId())) {
            SmartRole role = roleService.getById(query.getRoleId());
            if (Objects.nonNull(role) && Integer.valueOf(0).equals(role.getDeleted())) {
                query.setRoleName(role.getRoleName());
                query.setRoleDescription(role.getDescription());
            }
        }
        return query;
    }

    @Override
    public IPage<SmartUserDto> pageList(SmartUserVo vo) {
        IPage<SmartUserDto> list = baseMapper.pageList(vo);
        if (CollectionUtil.isNotEmpty(list.getRecords())) {
            //去掉密码
            list.getRecords().forEach(item -> item.setPassword(""));
            //收集角色id列表
            List<Long> roleIds = list.getRecords().stream().map(SmartUserDto::getRoleId).filter(Objects::nonNull).distinct().collect(Collectors.toList());
            //根据id查询角色信息
            QueryWrapper<SmartRole> roleWrapper = new QueryWrapper<>();
            roleWrapper.eq(SmartRole.DELETED, 0);
            roleWrapper.and(wrapper -> wrapper.in(SmartRole.ID, roleIds));
            List<SmartRole> roleList = roleService.list(roleWrapper);
            //将角色信息列表转为map, key为id
            Map<Long, SmartRole> roleMap = Maps.newHashMapWithExpectedSize(roleList.size());
            roleList.forEach(item -> roleMap.put(item.getId(), item));
            //设置角色名
            list.getRecords().forEach(item -> {
                if (Objects.nonNull(item.getRoleId()) && Objects.nonNull(roleMap.get(item.getRoleId()))) {
                    item.setRoleName(roleMap.get(item.getRoleId()).getRoleName());
                    item.setRoleDescription(roleMap.get(item.getRoleId()).getDescription());
                }
            });
        }
        return list;
    }

    @Override
    public SmartUserDto add(SmartUserVo vo) {
        log.info("添加用户, 入参: {}, localTime: {}, time: {}", JSON.toJSONString(vo), DateUtil.now(), DateUtil.current());
        validateUser(vo, Boolean.FALSE);
        //校验通过, 进行添加
        SmartUser entity = SmartUser.toEntity(vo);
        //密码加密
        entity.setPassword(passwordEncoder.encode(vo.getPassword()));
        entity.setUpdateTime(null);
        entity.setCreateTime(null);
        int effectRows = baseMapper.insert(entity);
        log.info("添加用户完成, 影响行数: {}, id: {}, localTime: {}, time: {}", effectRows, entity.getId(), DateUtil.now(), DateUtil.current());
        //去掉密码
        entity.setPassword("");
        return SmartUserDto.toDto(entity);
    }

    @Override
    public SmartUserDto edit(SmartUserVo vo) {
        log.info("修改用户, 入参: {}, localTime: {}, time: {}", JSON.toJSONString(vo), DateUtil.now(), DateUtil.current());
        validateUser(vo, Boolean.TRUE);
        //校验通过, 进行修改
        SmartUser entity = SmartUser.toEntity(vo);
        entity.setPassword(null);
        entity.setUpdateTime(new Date());
        entity.setCreateTime(null);
        int effectRows = baseMapper.updateById(entity);
        log.info("修改用户完成, 影响行数: {}, id: {}, localTime: {}, time: {}", effectRows, entity.getId(), DateUtil.now(), DateUtil.current());
        //去掉密码
        entity.setPassword("");
        return SmartUserDto.toDto(entity);
    }

    @Override
    public SmartUserDto del(Long id) {
        log.info("删除用户, id: {}, localTime: {}, time: {}", id, DateUtil.now(), DateUtil.current());
        UpdateWrapper<SmartUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(SmartUser.ID, id);
        SmartUser entity = new SmartUser();
        entity.setId(id);
        entity.setDeleted(1);
        entity.setUpdateTime(new Date());
        entity.setCreateTime(null);
        int effectRows = baseMapper.update(entity, updateWrapper);
        log.info("删除用户完成, 影响行数: {}, id: {}, localTime: {}, time: {}", effectRows, entity.getId(), DateUtil.now(), DateUtil.current());
        //去掉密码
        entity.setPassword("");
        return SmartUserDto.toDto(entity);
    }

    @Override
    public SmartUserDto resetPassword(SmartUserVo vo) {
        log.info("修改密码, userVo: {}, localTime: {}, time: {}", JSON.toJSONString(vo), DateUtil.now(), DateUtil.current());
        SmartUserDto query = query(vo);
        if (Objects.isNull(query)) { //如果没有查询到用户信息
            throw new BusinessException(HttpStatus.EXPECTATION_FAILED, "未找到用户信息");
        }
        query.setPassword(passwordEncoder.encode(vo.getPassword()));
        SmartUser entity = SmartUser.toEntity(query);
        entity.setUpdateTime(new Date());
        entity.setCreateTime(null);
        boolean isSuccess = updateById(entity);
        log.info("修改密码完成, query: {}, res: {}, localTime: {}, time: {}", JSON.toJSONString(query), isSuccess, DateUtil.now(), DateUtil.current());
        //去掉密码
        query.setPassword("");
        return query;
    }


    public void validateUser(SmartUserVo vo, Boolean isEdit) {
        //校验用户名
        if (StrUtil.isBlankIfStr(vo.getUsername())) {
            throw new BusinessException(HttpStatus.PAYMENT_REQUIRED, "用户名不允许为空");
        }
        //校验手机号
        if (StrUtil.isBlankIfStr(vo.getPhone())) {
            throw new BusinessException(HttpStatus.PAYMENT_REQUIRED, "手机号不允许为空");
        }
        //校验手机格式
        if (!Validator.isMobile(vo.getPhone())) {
            throw new BusinessException(HttpStatus.PAYMENT_REQUIRED, "请填入正确的手机号 ");
        }
        //检查用户名和手机号是否被占用
        QueryWrapper<SmartUser> userNameQuery = new QueryWrapper<>();
        userNameQuery.eq(SmartUser.DELETED, 0);
        userNameQuery.and(wrapper -> wrapper.eq(SmartUser.USERNAME, vo.getUsername()));
        SmartUser usernameUser = baseMapper.selectOne(userNameQuery);
        if (Objects.nonNull(usernameUser) && BooleanUtil.isFalse(isEdit)) {
            throw new BusinessException(HttpStatus.IM_USED, "该用户名已被占用, 请更换");
        } else if (Objects.nonNull(usernameUser) && BooleanUtil.isTrue(isEdit) && BooleanUtil.isFalse(StrUtil.equals(usernameUser.getUsername(), vo.getUsername()))) {
            throw new BusinessException(HttpStatus.IM_USED, "该用户名已被占用, 请更换");
        }
        QueryWrapper<SmartUser> phoneQuery = new QueryWrapper<>();
        phoneQuery.eq(SmartUser.DELETED, 0);
        phoneQuery.and(wrapper -> wrapper.eq(SmartUser.PHONE, vo.getPhone()));
        SmartUser phoneUser = baseMapper.selectOne(phoneQuery);
        if (Objects.nonNull(phoneUser) && BooleanUtil.isFalse(isEdit)) {
            throw new BusinessException(HttpStatus.IM_USED, "该手机号已被注册");
        } else if (Objects.nonNull(phoneUser) && BooleanUtil.isTrue(isEdit) && BooleanUtil.isFalse(StrUtil.equals(phoneUser.getPhone(), vo.getPhone()))) {
            throw new BusinessException(HttpStatus.IM_USED, "该手机号已被注册");
        }
    }
}
