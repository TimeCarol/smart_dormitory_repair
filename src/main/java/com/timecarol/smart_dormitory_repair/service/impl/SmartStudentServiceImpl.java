package com.timecarol.smart_dormitory_repair.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timecarol.smart_dormitory_repair.dto.response.SmartStudentDto;
import com.timecarol.smart_dormitory_repair.dto.response.SmartUserDto;
import com.timecarol.smart_dormitory_repair.entity.SmartStudent;
import com.timecarol.smart_dormitory_repair.exception.BusinessException;
import com.timecarol.smart_dormitory_repair.mapper.SmartStudentMapper;
import com.timecarol.smart_dormitory_repair.service.ISmartStudentService;
import com.timecarol.smart_dormitory_repair.service.ISmartUserService;
import com.timecarol.smart_dormitory_repair.vo.SmartStudentVo;
import com.timecarol.smart_dormitory_repair.vo.SmartUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * 学生信息表 服务实现类
 *
 * @author timecarol
 * @since 2022-10-07
 */
@Service
public class SmartStudentServiceImpl extends ServiceImpl<SmartStudentMapper, SmartStudent> implements ISmartStudentService {

    @Autowired
    ISmartUserService userService;

    @Override
    public SmartStudentDto query(SmartStudentVo vo) {
        return baseMapper.query(vo);
    }

    @Override
    public IPage<SmartStudentDto> pageList(SmartStudentVo vo) {
        return baseMapper.pageList(vo);
    }

    @Override
    public SmartStudentDto add(SmartStudentVo vo) {
        Validator.validateMobile(vo.getPhone(), "非法手机号");
        Validator.validateCitizenIdNumber(vo.getIdCard(), "非法身份证");
        Validator.validateNotEmpty(vo.getUsername(), "用户名不允许为空");
        //检查用户表中是否存在对应username
        SmartUserVo userVo = new SmartUserVo();
        userVo.setUsername(vo.getUsername());
        SmartUserDto userDto = userService.query(userVo);
        if (Objects.isNull(userDto)) {
            throw new BusinessException(HttpStatus.EXPECTATION_FAILED, "用户名不存在");
        }
        vo.setCreateTime(null);
        vo.setUpdateTime(null);
        SmartStudent entity = SmartStudent.toEntity(vo);
        baseMapper.insert(entity);
        return SmartStudentDto.toDto(entity);
    }

    @Override
    public SmartStudentDto edit(SmartStudentVo vo) {
        Validator.validateNotNull(vo.getId(), "id不允许为空");
        if (!StrUtil.isBlankIfStr(vo.getPhone())) {
            Validator.validateMobile(vo.getPhone(), "非法手机号");
        }
        if (!StrUtil.isBlankIfStr(vo.getIdCard())) {
            Validator.validateCitizenIdNumber(vo.getIdCard(), "非法身份证");
        }
        if (!StrUtil.isBlankIfStr(vo.getUsername())) {
            //检查用户表中是否存在对应username
            SmartUserVo userVo = new SmartUserVo();
            userVo.setUsername(vo.getUsername());
            SmartUserDto userDto = userService.query(userVo);
            if (Objects.isNull(userDto)) {
                throw new BusinessException(HttpStatus.EXPECTATION_FAILED, "用户名不存在");
            }
        }
        SmartStudentDto query = query(vo);
        if (Objects.isNull(query)) {
            throw new BusinessException(HttpStatus.EXPECTATION_FAILED, "未找到学生信息");
        }
        vo.setCreateTime(null);
        vo.setUpdateTime(new Date());
        SmartStudent entity = SmartStudent.toEntity(vo);
        entity.setId(query.getId());
        baseMapper.updateById(entity);
        BeanUtils.copyProperties(entity, query);
        return query;
    }

    @Override
    public SmartStudentDto del(SmartStudentVo vo) {
        Validator.validateNotNull(vo.getId(), "id不允许为空");
        SmartStudentDto query = query(vo);
        if (Objects.isNull(query)) {
            throw new BusinessException(HttpStatus.EXPECTATION_FAILED, "未找到学生信息");
        }
        vo = new SmartStudentVo();
        vo.setId(query.getId());
        vo.setDeleted(1);
        vo.setUpdateTime(new Date());
        baseMapper.updateById(SmartStudent.toEntity(vo));
        return query;
    }
}
