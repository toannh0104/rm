package com.crypto.recommendation.service.projector;

import com.crypto.recommendation.dto.CryptoBasicView;
import com.crypto.recommendation.repository.entity.CryptoHistoryEntity;
import com.crypto.recommendation.repository.entity.CryptoHistoryEntity_;
import com.crypto.recommendation.repository.util.Projector;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class CryptoBasicViewProjector implements Projector<CryptoHistoryEntity, CryptoBasicView> {
    private static final String MIN = "min";
    private static final String MAX = "max";
    @Override
    public Selection<CryptoBasicView>[] getSelections(
            final Root<CryptoHistoryEntity> root,
            final CriteriaQuery query,
            final CriteriaBuilder cb,
            final Specification specification
    ) {
        return new Selection[] {
                root.get(CryptoHistoryEntity_.symbol).alias(CryptoHistoryEntity_.symbol.getName()),
                cb.min(root.get(CryptoHistoryEntity_.price)).alias(MIN),
                cb.max(root.get(CryptoHistoryEntity_.price)).alias(MAX),
        };
    }

    @Override
    public CryptoBasicView build(Tuple tuple) {
        CryptoBasicView cryptoBasicView = new CryptoBasicView();
        cryptoBasicView.setSymbol(tuple.get(CryptoHistoryEntity_.symbol.getName(), String.class));
        cryptoBasicView.setMin(tuple.get(MIN, BigDecimal.class));
        cryptoBasicView.setMax(tuple.get(MAX, BigDecimal.class));
        return cryptoBasicView;
    }

    @Override
    public Expression[] getGroupBy(Root<CryptoHistoryEntity> root, CriteriaBuilder criteriaBuilder) {
        List<Expression> expressions = new ArrayList<>();
        expressions.add(root.get(CryptoHistoryEntity_.symbol));
        return expressions.toArray(new Expression[expressions.size()]);
    }
}
