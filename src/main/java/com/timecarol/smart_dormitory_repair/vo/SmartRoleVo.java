package com.timecarol.smart_dormitory_repair.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timecarol.smart_dormitory_repair.dto.response.SmartRoleDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author timecarol
 * @since 2022-09-26
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "SmartRole对象", description = "")
public class SmartRoleVo extends Page<SmartRoleDto> implements Serializable {

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

    public long getPageIndex() {
        return super.getCurrent();
    }

    public long getPageSize() {
        return super.getSize();
    }

    public void setPageIndex(long pageIndex) {
        super.setCurrent(pageIndex);
    }

    public void setPageSize(long pageSize) {
        super.setSize(pageSize);
    }

    public static final String ID = "id";

    public static final String ROLE_NAME = "role_name";

    public static final String DESCRIPTION = "description";

    public static final String DELETED = "deleted";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

}

