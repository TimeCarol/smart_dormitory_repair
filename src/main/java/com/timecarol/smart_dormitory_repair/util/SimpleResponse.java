package com.timecarol.smart_dormitory_repair.util;

import com.timecarol.smart_dormitory_repair.exception.BusinessException;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class SimpleResponse<T> {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回内容
     */
    private String msg;

    /**
     * 数据对象
     */
    private T data;

    /**
     * 初始化一个新创建的 ReturnResult 对象，使其表示一个空消息。
     */
    public SimpleResponse() {
        this.code = HttpStatus.OK.value();
        this.msg = "成功";
    }

    /**
     * 初始化一个新创建的 ReturnResult 对象
     *
     * @param data 数据
     */
    public SimpleResponse(T data) {
        this();
        this.data = data;
    }

    /**
     * 初始化一个新创建的 ReturnResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public SimpleResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 初始化一个新创建的 ReturnResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public SimpleResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static <T> SimpleResponse<T> success() {
        return new SimpleResponse<>(HttpStatus.OK.value(), "成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static <T> SimpleResponse<T> success(T data) {
        return new SimpleResponse<>(HttpStatus.OK.value(), "成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static <T> SimpleResponse<T> success(String msg) {
        return new SimpleResponse<>(HttpStatus.OK.value(), msg);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> SimpleResponse<T> success(String msg, T data) {
        // 自定义状态码~
        return new SimpleResponse<>(HttpStatus.OK.value(), msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return 默认失败
     */
    public static <T> SimpleResponse<T> error() {
        return new SimpleResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "操作失败");
    }

    /**
     * 返回错误信息
     *
     * @param e 异常 - 自定义异常
     * @return 结果
     */
    public static <T> SimpleResponse<T> error(BusinessException e) {
        return new SimpleResponse<>(e.getCode(), e.getMsg());
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static <T> SimpleResponse<T> error(String msg) {
        return new SimpleResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static <T> SimpleResponse<T> error(String msg, T data) {
        return new SimpleResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static <T> SimpleResponse<T> error(int code, String msg) {
        return new SimpleResponse<>(code, msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static <T> SimpleResponse<T> error(int code, String msg, T data) {
        return new SimpleResponse<>(code, msg, data);
    }

    /**
     * 未认证
     *
     * @param <T> 。
     * @return 未认证失败信息
     */
    public static <T> SimpleResponse<T> unauthorized() {
        return new SimpleResponse<>(HttpStatus.UNAUTHORIZED.value(), "未登录", null);
    }
}
