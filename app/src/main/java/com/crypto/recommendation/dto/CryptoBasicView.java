package com.crypto.recommendation.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CryptoBasicView {
    private String symbol;
    private BigDecimal min;
    private BigDecimal max;
}
