package com.timecarol.smart_dormitory_repair.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.timecarol.smart_dormitory_repair.dto.response.SmartMaintainerDto;
import com.timecarol.smart_dormitory_repair.service.ISmartMaintainerService;
import com.timecarol.smart_dormitory_repair.util.SimpleResponse;
import com.timecarol.smart_dormitory_repair.vo.SmartMaintainerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author timecarol
 * @since 2022-10-06
 */
@Api(description = "维修工人控制器")
@Slf4j
@RestController
@SaCheckLogin
@RequestMapping("/maintainer")
public class SmartMaintainerController extends BaseController {

    @Autowired
    private ISmartMaintainerService smartMaintainerService;

    @PostMapping(value = "/query")
    @ApiOperation("维修工人信息查询单个")
    public SimpleResponse<SmartMaintainerDto> getSmartMaintainer(@Valid @RequestBody SmartMaintainerVo vo) {
        return new SimpleResponse<>(smartMaintainerService.query(vo));
    }

    @PostMapping(value = "/list")
    @ApiOperation("维修工人信息查询全部")
    public SimpleResponse<IPage<SmartMaintainerDto>> pageList(@Valid @RequestBody SmartMaintainerVo vo) {
        return new SimpleResponse<>(smartMaintainerService.pageList(vo));
    }

    @PostMapping(value = "/add")
    @ApiOperation("维修工人信息新增")
    public SimpleResponse<SmartMaintainerDto> add(@Valid @RequestBody SmartMaintainerVo vo) {
        return new SimpleResponse<>(smartMaintainerService.add(vo));
    }

    @PostMapping(value = "/edit")
    @ApiOperation("维修工人信息修改")
    public SimpleResponse<SmartMaintainerDto> edit(@Valid @RequestBody SmartMaintainerVo vo) {
        return new SimpleResponse<>(smartMaintainerService.edit(vo));
    }


    @PostMapping(value = "/del")
    @ApiOperation("维修工人信息删除(单个条目)")
    public SimpleResponse<SmartMaintainerDto> del(@Valid @RequestBody SmartMaintainerVo vo) {
        return new SimpleResponse<>(smartMaintainerService.del(vo));
    }
}
