package com.timecarol.smart_dormitory_repair.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.timecarol.smart_dormitory_repair.dto.response.SmartMaintainerDto;
import com.timecarol.smart_dormitory_repair.entity.SmartMaintainer;
import com.timecarol.smart_dormitory_repair.vo.SmartMaintainerVo;

/**
 * 维修工人信息表 Mapper 接口
 *
 * @author timecarol
 * @since 2022-10-06
 */
public interface SmartMaintainerMapper extends BaseMapper<SmartMaintainer> {

    SmartMaintainerDto query(SmartMaintainerVo vo);

    IPage<SmartMaintainerDto> pageList(SmartMaintainerVo vo);
}
