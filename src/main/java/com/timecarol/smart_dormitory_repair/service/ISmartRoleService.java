package com.timecarol.smart_dormitory_repair.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.timecarol.smart_dormitory_repair.dto.response.SmartRoleDto;
import com.timecarol.smart_dormitory_repair.entity.SmartRole;
import com.timecarol.smart_dormitory_repair.vo.SmartRoleVo;
import com.timecarol.smart_dormitory_repair.vo.SmartUserVo;

/**
 * @author timecarol
 * @since 2022-09-26
 */
public interface ISmartRoleService extends IService<SmartRole> {
    SmartRoleDto query(SmartRoleVo vo);

    IPage<SmartRoleDto> pageList(SmartRoleVo vo);

    Boolean add(SmartRoleVo vo);

    SmartRoleDto edit(SmartRoleVo vo);

    SmartRoleDto del(SmartRoleVo vo);

    SmartRoleDto queryByUser(SmartUserVo vo);
}


