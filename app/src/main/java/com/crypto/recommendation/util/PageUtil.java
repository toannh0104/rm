package com.crypto.recommendation.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageUtil {
    public static Pageable buildPageRequest(final Integer limit) {
        return limit != null ?
                PageRequest.of(0, limit) :
                Pageable.unpaged();
    }

    public static Pageable buildPageRequest(final Sort.Direction direction, final String column) {
        return PageRequest.of(0, 1, Sort.by(direction, column));
    }
}
