package com.timecarol.smart_dormitory_repair.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.timecarol.smart_dormitory_repair.dto.response.SmartStudentDto;
import com.timecarol.smart_dormitory_repair.vo.SmartStudentVo;
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
 * @since 2022-10-07
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@TableName("smart_student")
@ApiModel(value = "SmartStudent对象", description = "学生信息表")
public class SmartStudent implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "学生学号")
    @TableField("student_number")
    private String studentNumber;

    @ApiModelProperty(value = "学生用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "学生姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "性别")
    @TableField("gender")
    private Integer gender;

    @ApiModelProperty(value = "电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "身份证")
    @TableField("id_card")
    private String idCard;

    @ApiModelProperty(value = "班级")
    @TableField("grade")
    private String grade;

    @ApiModelProperty(value = "籍贯")
    @TableField("native_place")
    private String nativePlace;

    @ApiModelProperty(value = "照片地址")
    @TableField("photo_url")
    private String photoUrl;

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

    public static final String STUDENT_NUMBER = "student_number";

    public static final String USERNAME = "username";

    public static final String NAME = "name";

    public static final String GENDER = "gender";

    public static final String PHONE = "phone";

    public static final String ID_CARD = "id_card";

    public static final String GRADE = "grade";

    public static final String NATIVE_PLACE = "native_place";

    public static final String PHOTO_URL = "photo_url";

    public static final String DELETED = "deleted";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static SmartStudent toEntity(SmartStudentDto dto) {
        if (Objects.nonNull(dto)) {
            SmartStudent smartStudent = new SmartStudent();
            BeanUtils.copyProperties(dto, smartStudent);
            return smartStudent;
        }
        return null;
    }

    public static SmartStudent toEntity(SmartStudentVo vo) {
        if (Objects.nonNull(vo)) {
            SmartStudent smartStudent = new SmartStudent();
            BeanUtils.copyProperties(vo, smartStudent);
            return smartStudent;
        }
        return null;
    }
}

