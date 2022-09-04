package com.timecarol.smart_dormitory_repair.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 5162710183457828792L;

    private Integer code;

    private String msg;

    public BusinessException() {}

    public BusinessException(HttpStatus status, String msg) {
        super(msg);
        this.code = status.value();
        this.msg = msg;
    }
}
