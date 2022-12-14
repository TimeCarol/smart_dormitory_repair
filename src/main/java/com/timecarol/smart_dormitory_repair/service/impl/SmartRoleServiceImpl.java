package com.timecarol.smart_dormitory_repair.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timecarol.smart_dormitory_repair.dto.response.SmartRoleDto;
import com.timecarol.smart_dormitory_repair.dto.response.SmartUserDto;
import com.timecarol.smart_dormitory_repair.entity.SmartRole;
import com.timecarol.smart_dormitory_repair.exception.BusinessException;
import com.timecarol.smart_dormitory_repair.mapper.SmartRoleMapper;
import com.timecarol.smart_dormitory_repair.service.ISmartRoleService;
import com.timecarol.smart_dormitory_repair.service.ISmartUserService;
import com.timecarol.smart_dormitory_repair.vo.SmartRoleVo;
import com.timecarol.smart_dormitory_repair.vo.SmartUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

/**
 * 服务实现类
 *
 * @author timecarol
 * @since 2022-09-26
 */
@Slf4j
@Service
@Transactional
public class SmartRoleServiceImpl extends ServiceImpl<SmartRoleMapper, SmartRole> implements ISmartRoleService {

    @Autowired
    ISmartUserService smartUserService;

    @Override
    public SmartRoleDto query(SmartRoleVo vo) {
        return baseMapper.query(vo);
    }

    @Override
    public IPage<SmartRoleDto> pageList(SmartRoleVo vo) {
        return baseMapper.pageList(vo);
    }

    @Override
    public Boolean add(SmartRoleVo vo) {
        SmartRole entity = SmartRole.toEntity(vo);
        vo.setId(null);
        validateRole(vo);
        entity.setCreateTime(null);
        entity.setUpdateTime(null);
        boolean save = save(entity);
        log.info("角色添加, roleVo: {}, res: {}, localTime: {}, time: {}", JSON.toJSONString(vo), save, DateUtil.now(), DateUtil.current());
        vo.setId(entity.getId());
        return save;
    }

    @Override
    public SmartRoleDto edit(SmartRoleVo vo) {
        log.info("角色编辑, roleVo: {}, localTime: {}, time: {}", JSON.toJSONString(vo), DateUtil.now(), DateUtil.current());
        validateRole(vo);
        SmartRoleDto query = query(vo);
        if (Objects.isNull(query)) {
            throw new BusinessException(HttpStatus.EXPECTATION_FAILED, "角色信息未找到");
        }
        vo.setUpdateTime(new Date());
        vo.setCreateTime(null);
        vo.setId(query.getId());
        SmartRole smartRole = SmartRole.toEntity(vo);
        updateById(smartRole);
        return SmartRoleDto.toDto(smartRole);
    }

    private void validateRole(SmartRoleVo vo) {
        if (StrUtil.isBlankIfStr(vo.getRoleName())) {
            throw new BusinessException(HttpStatus.PAYMENT_REQUIRED, "角色名不允许为空");
        }
        SmartRoleDto query = query(vo);
        //编辑状态下角色名已存在
        if (Objects.nonNull(vo.getId()) && Objects.nonNull(query) && !NumberUtil.equals(query.getId(), vo.getId())) {
            throw new BusinessException(HttpStatus.PAYMENT_REQUIRED, "角色名已存在");
        }
        //添加状态下角色名已存在
        if (Objects.nonNull(query)) {
            throw new BusinessException(HttpStatus.PAYMENT_REQUIRED, "角色名已存在");
        }
    }

    @Override
    public SmartRoleDto del(SmartRoleVo vo) {
        log.info("角色删除, roleVo: {}, localTime: {}, time: {}", JSON.toJSONString(vo), DateUtil.now(), DateUtil.current());
        SmartRoleDto query = query(vo);
        if (Objects.isNull(query)) {
            throw new BusinessException(HttpStatus.EXPECTATION_FAILED, "角色信息未找到");
        }
        SmartRole entity = SmartRole.toEntity(query);
        entity.setDeleted(1);
        entity.setUpdateTime(new Date());
        entity.setCreateTime(null);
        updateById(entity);
        return SmartRoleDto.toDto(entity);
    }

    @Override
    public SmartRoleDto queryByUser(SmartUserVo vo) {
        SmartUserDto query = smartUserService.query(vo);
        if (Objects.isNull(query)) {
            throw new BusinessException(HttpStatus.EXPECTATION_FAILED, "用户信息未找到");
        }
        SmartRoleVo roleVo = new SmartRoleVo();
        roleVo.setId(query.getRoleId());
        SmartRoleDto roleDto = query(roleVo);
        if (Objects.isNull(roleDto)) {
            throw new BusinessException(HttpStatus.EXPECTATION_FAILED, "角色信息未找到");
        }
        return roleDto;
    }
}
