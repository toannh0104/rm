package com.crypto.recommendation.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class DatetimeFilterRequest {
    private LocalDateTime fromTimestamp;
    private LocalDateTime toTimestamp;
}
