package com.crypto.recommendation.service.specification;

import com.crypto.recommendation.controller.request.DatetimeFilterRequest;
import com.crypto.recommendation.repository.entity.CryptoHistoryEntity;
import com.crypto.recommendation.repository.entity.CryptoHistoryEntity_;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ReportSpecification {
    public static Specification<CryptoHistoryEntity> buildSpecification(
            final String crypto, final DatetimeFilterRequest request
    ) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getFromTimestamp() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get(CryptoHistoryEntity_.timestamp),
                                request.getFromTimestamp()
                        )
                );
            }
            if (request.getToTimestamp() != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get(CryptoHistoryEntity_.timestamp),
                                request.getToTimestamp()
                        )
                );
            }
            if (StringUtils.hasText(crypto)) {
                predicates.add(criteriaBuilder.equal(root.get(CryptoHistoryEntity_.symbol), crypto));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
