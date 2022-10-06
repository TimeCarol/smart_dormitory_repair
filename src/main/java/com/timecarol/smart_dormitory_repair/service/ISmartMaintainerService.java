package com.timecarol.smart_dormitory_repair.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.timecarol.smart_dormitory_repair.dto.response.SmartMaintainerDto;
import com.timecarol.smart_dormitory_repair.entity.SmartMaintainer;
import com.timecarol.smart_dormitory_repair.vo.SmartMaintainerVo;

/**
 * @author timecarol
 * @since 2022-10-06
 */
public interface ISmartMaintainerService extends IService<SmartMaintainer> {

    SmartMaintainerDto query(SmartMaintainerVo vo);

    IPage<SmartMaintainerDto> pageList(SmartMaintainerVo vo);

    SmartMaintainerDto add(SmartMaintainerVo vo);

    SmartMaintainerDto edit(SmartMaintainerVo vo);

    SmartMaintainerDto del(SmartMaintainerVo vo);

}


