package com.crypto.recommendation.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CryptoStatisticResponse {
    private BigDecimal oldest;
    private BigDecimal newest;
    private BigDecimal min;
    private BigDecimal max;
}
