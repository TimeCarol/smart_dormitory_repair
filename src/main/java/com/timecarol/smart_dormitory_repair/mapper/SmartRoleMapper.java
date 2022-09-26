package com.timecarol.smart_dormitory_repair.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.timecarol.smart_dormitory_repair.dto.response.SmartRoleDto;
import com.timecarol.smart_dormitory_repair.entity.SmartRole;
import com.timecarol.smart_dormitory_repair.vo.SmartRoleVo;

/**
 * Mapper 接口
 *
 * @author timecarol
 * @since 2022-09-26
 */
public interface SmartRoleMapper extends BaseMapper<SmartRole> {

    SmartRoleDto query(SmartRoleVo vo);

    IPage<SmartRoleDto> pageList(SmartRoleVo vo);
}
