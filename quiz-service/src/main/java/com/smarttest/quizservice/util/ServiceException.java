package com.smarttest.quizservice.util;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    private int code;

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }
}
