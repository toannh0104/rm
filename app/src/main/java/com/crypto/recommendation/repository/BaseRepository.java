package com.crypto.recommendation.repository;

import com.crypto.recommendation.repository.util.Projector;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class BaseRepository {

    @Autowired
    private EntityManager entityManager;

    public<E, T>List<T> findAll(
            final Specification<? extends E> specification,
            final Class<? extends  E> entityClazz,
            final Projector<? extends E, T> projector,
            final Pageable pageable
    ) {
        TypedQuery<Tuple> typedQuery = buildQuery(specification, entityClazz, projector, pageable);
        return typedQuery.getResultList().stream()
                .map(projector::build)
                .collect(Collectors.toList());
    }

    private <E, T> TypedQuery<Tuple> buildQuery(
            final Specification<? extends E> specification,
            final Class<? extends E> entityClazz,
            final Projector<? extends E, T> projector,
            final Pageable pageable
    ) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Tuple> query = cb.createTupleQuery();
        final Root root = query.from(entityClazz);

        query.multiselect(projector.getSelections(root, query, cb, specification))
                .distinct(projector.isDistinct())
                .where(specification.toPredicate(root, query, cb));

        final Expression[] expressions = projector.getGroupBy(root, cb);

        if(expressions != null) {
            query.groupBy(expressions);
        }

        if(pageable.getSort().isSorted()) {
            query.orderBy(QueryUtils.toOrders(pageable.getSort(), root, cb));
        }

        TypedQuery<Tuple> typedQuery = entityManager.createQuery(query);

        if(pageable.isPaged()) {
            typedQuery = typedQuery.setFirstResult((int) pageable.getOffset());
            typedQuery = typedQuery.setMaxResults(pageable.getPageSize());
        }
        return typedQuery;
    }
}
