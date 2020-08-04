package com.wowjoy.boot.exception;

import lombok.Data;

/**
 * @author scq
 * @date 2019/03/09 21:57:00
 */
@Data
public class CustomErrorType {

    private int value;
    private String message;

    public CustomErrorType() {
    }

    public CustomErrorType(int value) {
        this.value = value;
    }

    public CustomErrorType(int value, String message) {
        this.value = value;
        this.message = message;
    }
}
