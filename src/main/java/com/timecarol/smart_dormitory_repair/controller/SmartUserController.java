package com.timecarol.smart_dormitory_repair.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.base.Preconditions;
import com.timecarol.smart_dormitory_repair.dto.response.SmartUserDto;
import com.timecarol.smart_dormitory_repair.exception.BusinessException;
import com.timecarol.smart_dormitory_repair.service.ISmartUserService;
import com.timecarol.smart_dormitory_repair.util.SimpleResponse;
import com.timecarol.smart_dormitory_repair.vo.SmartUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

/**
 * @author timecarol
 * @since 2022-09-03
 */
@Api(description = "用户控制器")
@RestController
@RequestMapping("/user")
@SaCheckLogin
public class SmartUserController extends BaseController {

    @Autowired
    private ISmartUserService smartUserService;

    @PostMapping("/query")
    @ApiOperation("SmartUser查询单个")
    public SimpleResponse<SmartUserDto> query(@RequestBody SmartUserVo vo) {
        //根据id进行查询
        SmartUserDto dto = smartUserService.query(vo);
        //去掉密码
        dto.setPassword("");
        return new SimpleResponse<>(dto);
    }

    @PostMapping("/list")
    @ApiOperation("SmartUser查询全部")
    public SimpleResponse<IPage<SmartUserDto>> pageList(@RequestBody SmartUserVo vo) {
        return new SimpleResponse<>(smartUserService.pageList(vo));
    }

    @PostMapping("/add")
    @ApiOperation("SmartUser新增")
    public SimpleResponse<SmartUserDto> add(@RequestBody SmartUserVo vo) {
        return new SimpleResponse<>(smartUserService.add(vo));
    }

    @PostMapping("/edit")
    @ApiOperation("SmartUser修改, 无法修改密码")
    public SimpleResponse<SmartUserDto> edit(@Valid @RequestBody SmartUserVo vo) {
        return new SimpleResponse<>(smartUserService.edit(vo));
    }

    @PostMapping(value = "/del")
    @ApiOperation("SmartUser删除(单个条目)")
    public SimpleResponse<SmartUserDto> del(@RequestBody SmartUserVo vo) {
        Preconditions.checkArgument(Objects.nonNull(vo), "id不能为空");
        return new SimpleResponse<>(smartUserService.del(vo.getId()));
    }

    @PostMapping("/resetPassword")
    @ApiOperation("修改密码")
    public SimpleResponse<SmartUserDto> resetPassword(@Valid @RequestBody SmartUserVo vo) {
        SmartUserDto smartUser = getSmartUser();
        if (!StrUtil.equals(vo.getUsername(), smartUser.getUsername())) {
            throw new BusinessException(HttpStatus.NOT_ACCEPTABLE, "您没有权限修改密码");
        }
        return new SimpleResponse<>(smartUserService.resetPassword(vo));
    }
}
