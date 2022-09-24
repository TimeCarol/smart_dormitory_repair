package com.timecarol.smart_dormitory_repair.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timecarol.smart_dormitory_repair.dto.response.SmartUserDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SmartUserVo extends Page<SmartUserDto> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "头像地址")
    private String avatarUrl;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty("当前页")
    private Long pageIndex;

    @ApiModelProperty("页大小")
    private Long pageSize;

}
