package com.timecarol.smart_dormitory_repair.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timecarol.smart_dormitory_repair.dto.response.SmartUserDto;
import com.timecarol.smart_dormitory_repair.entity.SmartUser;
import com.timecarol.smart_dormitory_repair.exception.BusinessException;
import com.timecarol.smart_dormitory_repair.mapper.SmartUserMapper;
import com.timecarol.smart_dormitory_repair.service.ISmartUserService;
import com.timecarol.smart_dormitory_repair.vo.SmartUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 服务实现类
 *
 * @author timecarol
 * @since 2022-09-03
 */
@Slf4j
@Service
public class SmartUserServiceImpl extends ServiceImpl<SmartUserMapper, SmartUser> implements ISmartUserService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public SmartUserDto query(SmartUserVo vo) {
        return baseMapper.query(vo);
    }

    @Override
    public IPage<SmartUserDto> pageList(SmartUserVo vo) {
        IPage<SmartUserDto> list = baseMapper.pageList(vo);
        if (CollectionUtil.isNotEmpty(list.getRecords())) {
            //去掉密码
            list.getRecords().forEach(item -> item.setPassword(""));
        }
        return list;
    }

    @Override
    public SmartUserDto add(SmartUserVo vo) {
        log.info("添加用户, 入参: {}, localTime: {}, time: {}", JSON.toJSONString(vo), DateUtil.now(), DateUtil.current());
        validateUser(vo);
        //校验通过, 进行添加
        SmartUser entity = SmartUser.toEntity(vo);
        //密码加密
        entity.setPassword(passwordEncoder.encode(vo.getPassword()));
        int effectRows = baseMapper.insert(entity);
        log.info("添加用户完成, 影响行数: {}, id: {}, localTime: {}, time: {}", effectRows, entity.getId(), DateUtil.now(), DateUtil.current());
        //去掉密码
        entity.setPassword("");
        return SmartUserDto.toDto(entity);
    }

    @Override
    public SmartUserDto edit(SmartUserVo vo) {
        log.info("修改用户, 入参: {}, localTime: {}, time: {}", JSON.toJSONString(vo), DateUtil.now(), DateUtil.current());
        validateUser(vo);
        //校验通过, 进行修改
        SmartUser entity = SmartUser.toEntity(vo);
        entity.setPassword(null);
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
        int effectRows = baseMapper.update(entity, updateWrapper);
        log.info("删除用户完成, 影响行数: {}, id: {}, localTime: {}, time: {}", effectRows, entity.getId(), DateUtil.now(), DateUtil.current());
        //去掉密码
        entity.setPassword("");
        return SmartUserDto.toDto(entity);
    }

    @Override
    public SmartUserDto resetPassword(SmartUserVo vo) {
        return null;
    }


    public void validateUser(SmartUserVo vo) {
        //校验用户名
        if (StrUtil.isBlankIfStr(vo.getUsername())) {
            throw new BusinessException(HttpStatus.PAYMENT_REQUIRED, "用户名不允许为空");
        }
        //校验手机号
        if (StrUtil.isBlankIfStr(vo.getPhone())) {
            throw new BusinessException(HttpStatus.PAYMENT_REQUIRED, "手机号不允许为空");
        }
        //校验手机格式
        String regPattern = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        Pattern pattern = Pattern.compile(regPattern);
        Matcher matcher = pattern.matcher(vo.getPhone());
        boolean isMatch = matcher.matches();
        if (!isMatch) {
            throw new BusinessException(HttpStatus.PAYMENT_REQUIRED, "请填入正确的手机号 ");
        }
        //检查用户名和手机号是否被占用
        QueryWrapper<SmartUser> userNameQuery = new QueryWrapper<>();
        userNameQuery.eq(SmartUser.DELETED, 0);
        userNameQuery.and(wrapper -> wrapper.eq(SmartUser.USERNAME, vo.getUsername()));
        SmartUser usernameUser = baseMapper.selectOne(userNameQuery);
        if (usernameUser != null) {
            throw new BusinessException(HttpStatus.IM_USED, "该用户名已被占用, 请更换");
        }
        QueryWrapper<SmartUser> phoneQuery = new QueryWrapper<>();
        phoneQuery.eq(SmartUser.DELETED, 0);
        phoneQuery.and(wrapper -> wrapper.eq(SmartUser.PHONE, vo.getPhone()));
        SmartUser phoneUser = baseMapper.selectOne(phoneQuery);
        if (phoneUser != null) {
            throw new BusinessException(HttpStatus.IM_USED, "该手机号已被注册");
        }
    }
}
