package com.crypto.recommendation.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(callSuper = true)
public class CryptoFilterRequest extends DatetimeFilterRequest {

    @ToString.Exclude
    private Integer limit = 1;
}
