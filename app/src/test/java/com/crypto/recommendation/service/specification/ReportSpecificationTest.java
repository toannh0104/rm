package com.crypto.recommendation.service.specification;

import com.crypto.recommendation.controller.request.CryptoFilterRequest;
import com.crypto.recommendation.controller.request.DatetimeFilterRequest;
import com.crypto.recommendation.repository.entity.CryptoHistoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;

public class ReportSpecificationTest {

    @Test
    void test_buildSpecification_whenFromDatetime_returnSpecification() {
        Root root = mock(Root.class);
        CriteriaQuery query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        DatetimeFilterRequest request = new CryptoFilterRequest();
        request.setFromTimestamp(LocalDateTime.now());
        Specification<CryptoHistoryEntity> specification = ReportSpecification.buildSpecification(null, request);
        specification.toPredicate(root, query, cb);
        //TODO verify specification
    }

    @Test
    void test_buildSpecification_whenToDatetime_returnSpecification() {
        Root root = mock(Root.class);
        CriteriaQuery query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        String crypto = "BTC";
        DatetimeFilterRequest request = new CryptoFilterRequest();
        request.setToTimestamp(LocalDateTime.now());
        Specification<CryptoHistoryEntity> specification = ReportSpecification.buildSpecification(crypto, request);
        specification.toPredicate(root, query, cb);
        //TODO verify specification
    }

    @Test
    void test_buildSpecification_whenCrypto_returnSpecification() {
        Root root = mock(Root.class);
        CriteriaQuery query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        String crypto = "BTC";
        DatetimeFilterRequest request = new CryptoFilterRequest();
        Specification<CryptoHistoryEntity> specification = ReportSpecification.buildSpecification(crypto, request);
        specification.toPredicate(root, query, cb);
        //TODO verify specification
    }
}
