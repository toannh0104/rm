package com.crypto.recommendation.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class GeneralResponse<T> implements Serializable {
    private ResponseStatus status;
    private T data;
}
