package com.wowjoy.boot.exception;

import lombok.Data;

/**
 * @author scq
 * @date 2019/03/09 20:41:00
 */
@Data
public class MyException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String code;
    private String msg;

    public MyException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
