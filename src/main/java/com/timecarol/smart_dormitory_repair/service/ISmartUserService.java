package com.timecarol.smart_dormitory_repair.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.timecarol.smart_dormitory_repair.dto.response.SmartUserDto;
import com.timecarol.smart_dormitory_repair.entity.SmartUser;
import com.timecarol.smart_dormitory_repair.vo.SmartUserVo;

import java.util.List;

/**
 * @author timecarol
 * @since 2022-09-03
 */
public interface ISmartUserService extends IService<SmartUser> {

    SmartUserDto query(SmartUserVo vo);

    List<SmartUserDto> pageList(SmartUserVo vo);

    SmartUserDto add(SmartUserVo vo);

    SmartUserDto edit(SmartUserVo vo);

    SmartUserDto del(Long id);

    SmartUserDto resetPassword(SmartUserVo vo);
}


