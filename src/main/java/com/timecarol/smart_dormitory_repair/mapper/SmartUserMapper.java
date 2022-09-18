package com.timecarol.smart_dormitory_repair.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timecarol.smart_dormitory_repair.dto.response.SmartUserDto;
import com.timecarol.smart_dormitory_repair.entity.SmartUser;
import com.timecarol.smart_dormitory_repair.vo.SmartUserVo;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author timecarol
 * @since 2022-09-03
 */
public interface SmartUserMapper extends BaseMapper<SmartUser> {

    List<SmartUserDto> pageList(SmartUserVo vo);

    SmartUserDto query(SmartUserVo vo);
}
