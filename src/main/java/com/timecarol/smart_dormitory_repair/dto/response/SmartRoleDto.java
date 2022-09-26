package com.timecarol.smart_dormitory_repair.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timecarol.smart_dormitory_repair.entity.SmartRole;
import com.timecarol.smart_dormitory_repair.vo.SmartRoleVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author timecarol
 * @since 2022-09-26
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "SmartRole对象", description = "")
public class SmartRoleDto implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "角色名")
    private String roleName;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ApiModelProperty(value = "是否删除 0-未删除;1-已删除")
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    public static SmartRoleDto toDto(SmartRoleVo vo) {
        if (Objects.isNull(vo)) {
            return null;
        }
        SmartRoleDto dto = new SmartRoleDto();
        BeanUtils.copyProperties(vo, dto);
        return dto;
    }

    public static SmartRoleDto toDto(SmartRole entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        SmartRoleDto dto = new SmartRoleDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}

