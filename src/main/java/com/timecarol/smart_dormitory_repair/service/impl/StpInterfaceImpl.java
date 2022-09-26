package com.timecarol.smart_dormitory_repair.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.convert.Convert;
import com.google.common.collect.Lists;
import com.timecarol.smart_dormitory_repair.dto.response.SmartRoleDto;
import com.timecarol.smart_dormitory_repair.service.ISmartRoleService;
import com.timecarol.smart_dormitory_repair.vo.SmartUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 自定义权限验证接口扩展
 */
@Service
public class StpInterfaceImpl implements StpInterface {
    @Autowired
    ISmartRoleService smartRoleService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return Collections.emptyList();
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        SmartUserVo vo = new SmartUserVo();
        vo.setId(Convert.convert(Long.class, loginId));
        SmartRoleDto smartRoleDto = smartRoleService.queryByUser(vo);
        return Lists.newArrayList(Optional.ofNullable(smartRoleDto).map(SmartRoleDto::getRoleName).orElseGet(() -> ""));
    }
}
