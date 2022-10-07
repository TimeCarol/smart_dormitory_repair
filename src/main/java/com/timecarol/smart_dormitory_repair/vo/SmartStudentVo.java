package com.timecarol.smart_dormitory_repair.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timecarol.smart_dormitory_repair.dto.response.SmartStudentDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author timecarol
 * @since 2022-10-07
 */
@Data
public class SmartStudentVo extends Page<SmartStudentDto> {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "学生学号")
    private String studentNumber;

    @ApiModelProperty(value = "学生用户名")
    private String username;

    @ApiModelProperty(value = "学生姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "班级")
    private String grade;

    @ApiModelProperty(value = "籍贯")
    private String nativePlace;

    @ApiModelProperty(value = "照片地址")
    private String photoUrl;

    @ApiModelProperty(value = "是否删除 0-未删除;1-已删除")
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}

