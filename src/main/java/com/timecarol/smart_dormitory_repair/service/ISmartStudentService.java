package com.timecarol.smart_dormitory_repair.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.timecarol.smart_dormitory_repair.dto.response.SmartStudentDto;
import com.timecarol.smart_dormitory_repair.entity.SmartStudent;
import com.timecarol.smart_dormitory_repair.vo.SmartStudentVo;

/**
 * @author timecarol
 * @since 2022-10-07
 */
public interface ISmartStudentService extends IService<SmartStudent> {

    /**
     * 学生信息表查询
     */
    SmartStudentDto query(SmartStudentVo vo);

    /**
     * 学生信息表分页查询
     */
    IPage<SmartStudentDto> pageList(SmartStudentVo vo);

    /**
     * 学生信息表新增
     */
    SmartStudentDto add(SmartStudentVo vo);

    /**
     * 学生信息表修改
     */
    SmartStudentDto edit(SmartStudentVo vo);

    /**
     * 学生信息表删除(单个条目)
     */
    SmartStudentDto del(SmartStudentVo vo);
}


