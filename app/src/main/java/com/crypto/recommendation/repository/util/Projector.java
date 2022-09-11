package com.crypto.recommendation.repository.util;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;

public interface Projector<E, T> {

    Selection<T>[] getSelections(Root<E> root, CriteriaQuery query, CriteriaBuilder criteriaBuilder, Specification specification);
    T build(Tuple tuple);

    default Expression<T>[] getGroupBy(Root<E> root, CriteriaBuilder criteriaBuilder) {
        return new Expression[0];
    }

    default Boolean isDistinct() {
        return Boolean.FALSE;
    }

}
