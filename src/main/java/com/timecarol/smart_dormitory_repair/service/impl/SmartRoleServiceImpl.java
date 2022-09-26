package com.timecarol.smart_dormitory_repair.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timecarol.smart_dormitory_repair.dto.response.SmartRoleDto;
import com.timecarol.smart_dormitory_repair.entity.SmartRole;
import com.timecarol.smart_dormitory_repair.exception.BusinessException;
import com.timecarol.smart_dormitory_repair.mapper.SmartRoleMapper;
import com.timecarol.smart_dormitory_repair.service.ISmartRoleService;
import com.timecarol.smart_dormitory_repair.vo.SmartRoleVo;
import lombok.extern.slf4j.Slf4j;
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
}
