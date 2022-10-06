package com.timecarol.smart_dormitory_repair.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timecarol.smart_dormitory_repair.dto.response.SmartMaintainerDto;
import com.timecarol.smart_dormitory_repair.entity.SmartMaintainer;
import com.timecarol.smart_dormitory_repair.exception.BusinessException;
import com.timecarol.smart_dormitory_repair.mapper.SmartMaintainerMapper;
import com.timecarol.smart_dormitory_repair.service.ISmartMaintainerService;
import com.timecarol.smart_dormitory_repair.vo.SmartMaintainerVo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * 维修工人信息表 服务实现类
 *
 * @author timecarol
 * @since 2022-10-06
 */
@Service
public class SmartMaintainerServiceImpl extends ServiceImpl<SmartMaintainerMapper, SmartMaintainer> implements ISmartMaintainerService {

    @Override
    public SmartMaintainerDto query(SmartMaintainerVo vo) {
        return baseMapper.query(vo);
    }

    @Override
    public IPage<SmartMaintainerDto> pageList(SmartMaintainerVo vo) {
        return baseMapper.pageList(vo);
    }

    @Override
    public SmartMaintainerDto add(SmartMaintainerVo vo) {
        SmartMaintainer entity = SmartMaintainer.toEntity(vo);
        entity.setCreateTime(null);
        entity.setUpdateTime(null);
        int effects = baseMapper.insert(entity);
        return SmartMaintainerDto.toDto(entity);
    }

    @Override
    public SmartMaintainerDto edit(SmartMaintainerVo vo) {
        SmartMaintainerDto query = query(vo);
        if (Objects.isNull(query)) {
            throw new BusinessException(HttpStatus.EXPECTATION_FAILED, "未找到工人信息");
        }
        SmartMaintainer entity = SmartMaintainer.toEntity(vo);
        entity.setCreateTime(null);
        entity.setUpdateTime(new Date());
        int effects = baseMapper.updateById(entity);
        return SmartMaintainerDto.toDto(entity);
    }

    @Override
    public SmartMaintainerDto del(SmartMaintainerVo vo) {
        SmartMaintainerDto query = query(vo);
        if (Objects.isNull(query)) {
            throw new BusinessException(HttpStatus.EXPECTATION_FAILED, "未找到工人信息");
        }
        SmartMaintainer entity = SmartMaintainer.toEntity(vo);
        entity.setCreateTime(null);
        entity.setUpdateTime(new Date());
        entity.setDeleted(1);
        int effects = baseMapper.updateById(entity);
        return SmartMaintainerDto.toDto(entity);
    }
}
