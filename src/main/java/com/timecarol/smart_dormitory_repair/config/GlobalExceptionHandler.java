package com.timecarol.smart_dormitory_repair.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.SaTokenException;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import com.timecarol.smart_dormitory_repair.exception.BusinessException;
import com.timecarol.smart_dormitory_repair.util.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public SimpleResponse<Object> handleNotLoginException(NotLoginException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e.toString());
        return SimpleResponse.error(HttpStatus.UNAUTHORIZED.value(), e.getLocalizedMessage());
    }

    @ExceptionHandler(SaTokenException.class)
    public SimpleResponse<Object> handleSaTokenException(SaTokenException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e.toString());
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public SimpleResponse<Object> handleHttpMessageNotReadableException(MissingServletRequestParameterException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e.toString());
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public SimpleResponse<Object> handlerIllegalArgumentException(IllegalArgumentException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e.toString());
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public SimpleResponse<Object> handleNullPointerException(NullPointerException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e.toString());
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(ClassCastException.class)
    public SimpleResponse<Object> handleClassCastException(ClassCastException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e.toString());
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public SimpleResponse<Object> handleIndexOutOfBoundsException(IndexOutOfBoundsException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e.toString());
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(SQLException.class)
    public SimpleResponse<Object> handleSQLException(SQLException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e.toString());
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(IOException.class)
    public SimpleResponse<Object> handleIOException(IOException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e.toString());
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(NoSuchMethodException.class)
    public SimpleResponse<Object> handleNoSuchMethodException(NoSuchMethodException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e.toString());
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(ClassNotFoundException.class)
    public SimpleResponse<Object> handleClassNotFoundException(ClassNotFoundException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e.toString());
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(BindException.class)
    public SimpleResponse<Object> handleBindException(BindException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e.toString());
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(ValidateException.class)
    public SimpleResponse<Object> handleValidateException(ValidateException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e.toString());
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public SimpleResponse<Object> handleValidationException(ValidationException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e.toString());
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public SimpleResponse<Object> handleBusinessException(BusinessException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e.toString());
        return SimpleResponse.error(e);
    }

    @ExceptionHandler(Exception.class)
    public SimpleResponse<Object> handleException(Exception e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e.toString());
        return SimpleResponse.error(e.getLocalizedMessage());
    }
}
