package com.timecarol.smart_dormitory_repair.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timecarol.smart_dormitory_repair.dto.response.SmartStudentDto;
import com.timecarol.smart_dormitory_repair.entity.SmartStudent;
import com.timecarol.smart_dormitory_repair.vo.SmartStudentVo;

/**
 * 学生信息表 Mapper 接口
 *
 * @author timecarol
 * @since 2022-10-07
 */
public interface SmartStudentMapper extends BaseMapper<SmartStudent> {

    SmartStudentDto query(SmartStudentVo vo);
}
