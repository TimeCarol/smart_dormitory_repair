package com.timecarol.smart_dormitory_repair.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.timecarol.smart_dormitory_repair.dto.response.SmartRoleDto;
import com.timecarol.smart_dormitory_repair.service.ISmartRoleService;
import com.timecarol.smart_dormitory_repair.util.SimpleResponse;
import com.timecarol.smart_dormitory_repair.vo.SmartRoleVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author timecarol
 * @since 2022-09-26
 */
@RestController
@RequestMapping("/role")
public class SmartRoleController extends BaseController {

    @Autowired
    private ISmartRoleService smartRoleService;

    @GetMapping("/query")
    @ApiOperation("SmartRole查询单个")
    public SimpleResponse<SmartRoleDto> query(@Valid @RequestBody SmartRoleVo vo) {
        return new SimpleResponse<>(smartRoleService.query(vo));
    }

    @GetMapping("/list")
    @ApiOperation("SmartRole查询全部")
    public SimpleResponse<IPage<SmartRoleDto>> pageList(@Valid @RequestBody SmartRoleVo vo) {
        return new SimpleResponse<>(smartRoleService.pageList(vo));
    }

    @PostMapping("/add")
    @ApiOperation("SmartRole新增")
    public SimpleResponse<SmartRoleDto> add(@Valid @RequestBody SmartRoleVo vo) {
        smartRoleService.add(vo);
        return new SimpleResponse<>(SmartRoleDto.toDto(vo));
    }

    @PostMapping("/edit")
    @ApiOperation("SmartRole修改")
    public SimpleResponse<SmartRoleDto> edit(@Valid @RequestBody SmartRoleVo vo) {
        return new SimpleResponse<>(smartRoleService.edit(vo));
    }


    @PostMapping(value = "/del")
    @ApiOperation("SmartRole删除(单个条目)")
    public SimpleResponse<SmartRoleDto> del(@Valid @RequestBody SmartRoleVo vo) {
        return new SimpleResponse<>(smartRoleService.del(vo));
    }
}
