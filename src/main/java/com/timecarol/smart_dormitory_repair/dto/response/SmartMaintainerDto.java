package com.timecarol.smart_dormitory_repair.dto.response;

import com.timecarol.smart_dormitory_repair.entity.SmartMaintainer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Objects;

@Data
public class SmartMaintainerDto {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "工人用户名")
    private String username;

    @ApiModelProperty(value = "工人姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "是否删除 0-未删除;1-已删除")
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    public static SmartMaintainerDto toDto(SmartMaintainer entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        SmartMaintainerDto dto = new SmartMaintainerDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
