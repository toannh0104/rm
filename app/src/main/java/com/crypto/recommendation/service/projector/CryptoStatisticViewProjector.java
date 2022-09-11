package com.crypto.recommendation.service.projector;

import com.crypto.recommendation.dto.CryptoTopView;
import com.crypto.recommendation.repository.entity.CryptoHistoryEntity;
import com.crypto.recommendation.repository.entity.CryptoHistoryEntity_;
import com.crypto.recommendation.repository.util.Projector;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.math.BigDecimal;

@Component
public class CryptoStatisticViewProjector implements Projector<CryptoHistoryEntity, CryptoTopView> {
    public Selection<CryptoTopView>[] getSelections(
            final Root<CryptoHistoryEntity> root,
            final CriteriaQuery query,
            final CriteriaBuilder cb,
            final Specification specification
    ) {
        return new Selection[] {
                root.get(CryptoHistoryEntity_.price).alias(CryptoHistoryEntity_.price.getName()),
        };
    }

    @Override
    public CryptoTopView build(Tuple tuple) {
        CryptoTopView cryptoTopView = new CryptoTopView();
        cryptoTopView.setPrice(tuple.get(CryptoHistoryEntity_.price.getName(), BigDecimal.class));
        return cryptoTopView;
    }

}
