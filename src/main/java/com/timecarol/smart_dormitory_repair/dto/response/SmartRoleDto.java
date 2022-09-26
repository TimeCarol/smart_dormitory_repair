package com.timecarol.smart_dormitory_repair.dto.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色名")
    @TableField("role_name")
    private String roleName;

    @ApiModelProperty(value = "角色描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "是否删除 0-未删除;1-已删除")
    @TableField("deleted")
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private Date updateTime;


    public static final String ID = "id";

    public static final String ROLE_NAME = "role_name";

    public static final String DESCRIPTION = "description";

    public static final String DELETED = "deleted";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

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

