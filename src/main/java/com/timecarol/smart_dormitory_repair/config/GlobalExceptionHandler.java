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
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e);
        return SimpleResponse.error(HttpStatus.UNAUTHORIZED.value(), e.getLocalizedMessage());
    }

    @ExceptionHandler(SaTokenException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public SimpleResponse<Object> handleSaTokenException(SaTokenException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e);
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public SimpleResponse<Object> handleHttpMessageNotReadableException(MissingServletRequestParameterException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e);
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public SimpleResponse<Object> handlerIllegalArgumentException(IllegalArgumentException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e);
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public SimpleResponse<Object> handleNullPointerException(NullPointerException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e);
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(ClassCastException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public SimpleResponse<Object> handleClassCastException(ClassCastException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e);
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public SimpleResponse<Object> handleIndexOutOfBoundsException(IndexOutOfBoundsException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e);
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public SimpleResponse<Object> handleSQLException(SQLException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e);
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public SimpleResponse<Object> handleIOException(IOException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e);
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(NoSuchMethodException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public SimpleResponse<Object> handleNoSuchMethodException(NoSuchMethodException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e);
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(ClassNotFoundException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public SimpleResponse<Object> handleClassNotFoundException(ClassNotFoundException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e);
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public SimpleResponse<Object> handleBindException(BindException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e);
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(ValidateException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public SimpleResponse<Object> handleValidateException(ValidateException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e);
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public SimpleResponse<Object> handleValidationException(ValidationException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e);
        return SimpleResponse.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public SimpleResponse<Object> handleBusinessException(BusinessException e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e);
        return SimpleResponse.error(e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.OK)
    public SimpleResponse<Object> handleException(Exception e) {
        log.info("发生异常, localTime: {}, time: {}, 异常信息: {}", DateUtil.now(), DateUtil.current(), e);
        return SimpleResponse.error(e.getLocalizedMessage());
    }
}
