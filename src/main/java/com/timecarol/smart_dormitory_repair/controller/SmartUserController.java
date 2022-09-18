package com.timecarol.smart_dormitory_repair.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.timecarol.smart_dormitory_repair.dto.response.SmartUserDto;
import com.timecarol.smart_dormitory_repair.service.ISmartUserService;
import com.timecarol.smart_dormitory_repair.util.SimpleResponse;
import com.timecarol.smart_dormitory_repair.vo.SmartUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    @GetMapping("/query")
    @ApiOperation("SmartUser查询单个")
    public SimpleResponse<SmartUserDto> query(@RequestParam("id") Long id,
                                              @RequestParam("username") String username,
                                              @RequestParam("phone") String phone) {
        SmartUserVo vo = new SmartUserVo();
        vo.setId(id);
        vo.setUsername(username);
        vo.setPhone(phone);
//根据id进行查询
        SmartUserDto dto = smartUserService.query(vo);
//去掉密码
        dto.setPassword("");
        return new SimpleResponse<>(dto);
    }

    @GetMapping("/list")
    @ApiOperation("SmartUser查询全部")
    public SimpleResponse<List<SmartUserDto>> pageList(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Long pageIndex,
                                                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Long pageSize) {
        SmartUserVo vo = new SmartUserVo();
        vo.setPageIndex(pageIndex);
        vo.setPageSize(pageSize);
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

    @DeleteMapping(value = "/del/{id}")
    @ApiOperation("SmartUser删除(单个条目)")
    public SimpleResponse<SmartUserDto> del(@NotNull(message = "{id不能为空}") @PathVariable Long id) {
        return new SimpleResponse<>(smartUserService.del(id));
    }

    @PostMapping("/resetPassword")
    @ApiOperation("修改密码")
    public SimpleResponse<SmartUserDto> resetPassword(@Valid @RequestBody SmartUserVo vo) {
        return new SimpleResponse<>(smartUserService.resetPassword(vo));
    }
}
