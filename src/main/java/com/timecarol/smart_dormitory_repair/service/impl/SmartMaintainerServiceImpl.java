package com.timecarol.smart_dormitory_repair.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timecarol.smart_dormitory_repair.dto.response.SmartMaintainerDto;
import com.timecarol.smart_dormitory_repair.dto.response.SmartUserDto;
import com.timecarol.smart_dormitory_repair.entity.SmartMaintainer;
import com.timecarol.smart_dormitory_repair.exception.BusinessException;
import com.timecarol.smart_dormitory_repair.mapper.SmartMaintainerMapper;
import com.timecarol.smart_dormitory_repair.service.ISmartMaintainerService;
import com.timecarol.smart_dormitory_repair.service.ISmartUserService;
import com.timecarol.smart_dormitory_repair.vo.SmartMaintainerVo;
import com.timecarol.smart_dormitory_repair.vo.SmartUserVo;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ISmartUserService smartUserService;

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
        SmartUserVo userVo = new SmartUserVo();
        userVo.setUsername(vo.getUsername());
        SmartUserDto queryUser = smartUserService.query(userVo);
        if (Objects.isNull(queryUser)) { //检查用户名是否存在
            throw new BusinessException(HttpStatus.EXPECTATION_FAILED, "用户名错误");
        }
        Validator.validateMobile(vo.getPhone(), "非法手机号");
        if (!StrUtil.isBlankIfStr(vo.getEmail())) {
            Validator.validateEmail(vo.getEmail(), "非法邮箱格式");
        }
        Validator.validateCitizenIdNumber(vo.getIdCard(), "非法身份证");
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
        SmartUserVo userVo = new SmartUserVo();
        userVo.setUsername(vo.getUsername());
        SmartUserDto queryUser = smartUserService.query(userVo);
        if (Objects.isNull(queryUser)) { //检查用户名是否存在
            throw new BusinessException(HttpStatus.EXPECTATION_FAILED, "用户名错误");
        }
        Validator.validateMobile(vo.getPhone(), "非法手机号");
        if (!StrUtil.isBlankIfStr(vo.getEmail())) {
            Validator.validateEmail(vo.getEmail(), "非法邮箱格式");
        }
        Validator.validateCitizenIdNumber(vo.getIdCard(), "非法身份证");
        SmartMaintainer entity = SmartMaintainer.toEntity(vo);
        entity.setCreateTime(null);
        entity.setUpdateTime(new Date());
        int effects = baseMapper.updateById(entity);
        return SmartMaintainerDto.toDto(entity);
    }

    @Override
    public SmartMaintainerDto del(SmartMaintainerVo vo) {
        Validator.validateNotNull(vo.getId(), "id不允许为空");
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
