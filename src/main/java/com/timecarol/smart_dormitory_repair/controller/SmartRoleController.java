package com.timecarol.smart_dormitory_repair.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.timecarol.smart_dormitory_repair.dto.response.SmartRoleDto;
import com.timecarol.smart_dormitory_repair.service.ISmartRoleService;
import com.timecarol.smart_dormitory_repair.util.SimpleResponse;
import com.timecarol.smart_dormitory_repair.vo.SmartRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author timecarol
 * @since 2022-09-26
 */
@Api(description = "角色信息控制器")
@RestController
@RequestMapping("/role")
public class SmartRoleController extends BaseController {

    @Autowired
    private ISmartRoleService smartRoleService;

    @GetMapping("/query")
    @ApiOperation("角色信息查询单个")
    public SimpleResponse<SmartRoleDto> query(@Valid @RequestBody SmartRoleVo vo) {
        return new SimpleResponse<>(smartRoleService.query(vo));
    }

    @GetMapping("/list")
    @ApiOperation("角色信息查询全部")
    public SimpleResponse<IPage<SmartRoleDto>> pageList(@Valid @RequestBody SmartRoleVo vo) {
        return new SimpleResponse<>(smartRoleService.pageList(vo));
    }

    @PostMapping("/add")
    @ApiOperation("角色信息新增")
    public SimpleResponse<SmartRoleDto> add(@Valid @RequestBody SmartRoleVo vo) {
        smartRoleService.add(vo);
        return new SimpleResponse<>(SmartRoleDto.toDto(vo));
    }

    @PostMapping("/edit")
    @ApiOperation("角色信息修改")
    public SimpleResponse<SmartRoleDto> edit(@Valid @RequestBody SmartRoleVo vo) {
        return new SimpleResponse<>(smartRoleService.edit(vo));
    }


    @PostMapping(value = "/del")
    @ApiOperation("角色信息删除(单个条目)")
    public SimpleResponse<SmartRoleDto> del(@Valid @RequestBody SmartRoleVo vo) {
        return new SimpleResponse<>(smartRoleService.del(vo));
    }
}
