package com.wowjoy.boot.common;

import lombok.Data;

/**
 * @author scq
 */
@Data
public class RException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public RException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public RException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public RException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public RException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
