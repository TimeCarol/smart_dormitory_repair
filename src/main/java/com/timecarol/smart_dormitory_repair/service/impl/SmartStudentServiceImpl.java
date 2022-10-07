package com.timecarol.smart_dormitory_repair.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timecarol.smart_dormitory_repair.dto.response.SmartStudentDto;
import com.timecarol.smart_dormitory_repair.entity.SmartStudent;
import com.timecarol.smart_dormitory_repair.mapper.SmartStudentMapper;
import com.timecarol.smart_dormitory_repair.service.ISmartStudentService;
import com.timecarol.smart_dormitory_repair.vo.SmartStudentVo;
import org.springframework.stereotype.Service;

/**
 * 学生信息表 服务实现类
 *
 * @author timecarol
 * @since 2022-10-07
 */
@Service
public class SmartStudentServiceImpl extends ServiceImpl<SmartStudentMapper, SmartStudent> implements ISmartStudentService {


    @Override
    public SmartStudentDto query(SmartStudentVo vo) {
        return baseMapper.query(vo);
    }

    @Override
    public IPage<SmartStudentDto> pageList(SmartStudentVo vo) {
        return null;
    }

    @Override
    public SmartStudentDto add(SmartStudentVo vo) {
        return null;
    }

    @Override
    public SmartStudentDto edit(SmartStudentVo vo) {
        return null;
    }

    @Override
    public SmartStudentDto del(SmartStudentVo vo) {
        return null;
    }
}
