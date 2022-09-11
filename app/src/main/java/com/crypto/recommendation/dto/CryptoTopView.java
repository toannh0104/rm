package com.crypto.recommendation.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CryptoTopView {
    private String symbol;
    private BigDecimal price;
}
