package com.crypto.recommendation.exception;

import com.crypto.recommendation.factory.ResponseStatusEnum;

public class ContentNotFoundException extends RuntimeException {
    private String code;
    public ContentNotFoundException(ResponseStatusEnum errorStatus) {
        super(errorStatus.getMessage());
        this.code = errorStatus.getCode();
    }
}
