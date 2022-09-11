package com.crypto.recommendation.factory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public enum ResponseStatusEnum {

    SUCCESS("00", "Success"),
    DATA_NOT_FOUND("01", "No data found"),
    UNSUPPORTED_FILE("02", "File type is not supported"),
    FILE_SIZE_TOO_LARGE("03", "File too large!");
    ;

    private final String code;
    private final String message;
}
