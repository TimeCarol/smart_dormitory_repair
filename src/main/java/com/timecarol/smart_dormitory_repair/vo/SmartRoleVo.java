package com.timecarol.smart_dormitory_repair.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.timecarol.smart_dormitory_repair.dto.response.SmartRoleDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author timecarol
 * @since 2022-09-26
 */
@Data
public class SmartRoleVo extends Page<SmartRoleDto> implements Serializable {

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

    public long getPageIndex() {
        return super.getCurrent();
    }

    public long getPageSize() {
        return super.getSize();
    }

    public SmartRoleVo setPageIndex(long pageIndex) {
        super.setCurrent(pageIndex);
        return this;
    }

    public SmartRoleVo setPageSize(long pageSize) {
        super.setSize(pageSize);
        return this;
    }

}

