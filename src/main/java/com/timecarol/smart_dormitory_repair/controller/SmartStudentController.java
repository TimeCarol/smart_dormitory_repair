package com.timecarol.smart_dormitory_repair.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.timecarol.smart_dormitory_repair.dto.response.SmartStudentDto;
import com.timecarol.smart_dormitory_repair.service.ISmartStudentService;
import com.timecarol.smart_dormitory_repair.util.SimpleResponse;
import com.timecarol.smart_dormitory_repair.vo.SmartStudentVo;
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
 * @since 2022-10-07
 */
@RestController
@Slf4j
@RequestMapping("/SmartStudent")
@Api(description = "学生信息控制器")
public class SmartStudentController extends BaseController {

    @Autowired
    ISmartStudentService smartStudentService;

    @PostMapping("/query")
    @ApiOperation("学生信息查询单个")
    public SimpleResponse<SmartStudentDto> query(@Valid @RequestBody SmartStudentVo vo) {
        return new SimpleResponse<>(smartStudentService.query(vo));
    }

    @PostMapping("/pageList")
    @ApiOperation("学生信息分页查询")
    public SimpleResponse<IPage<SmartStudentDto>> pageList(@Valid @RequestBody SmartStudentVo vo) {
        return new SimpleResponse<>(smartStudentService.pageList(vo));
    }

    @PostMapping("/add")
    @ApiOperation("学生信息新增")
    public SimpleResponse<SmartStudentDto> add(@Valid @RequestBody SmartStudentVo vo) {
        return new SimpleResponse<>(smartStudentService.add(vo));
    }

    @PostMapping("/edit")
    @ApiOperation("学生信息修改")
    public SimpleResponse<SmartStudentDto> edit(@Valid @RequestBody SmartStudentVo vo) {
        return new SimpleResponse<>(smartStudentService.edit(vo));
    }


    @PostMapping(value = "/del")
    @ApiOperation("学生信息删除(单个条目)")
    public SimpleResponse<SmartStudentDto> del(@Valid @RequestBody SmartStudentVo vo) {
        return new SimpleResponse<>(smartStudentService.del(vo));
    }
}
