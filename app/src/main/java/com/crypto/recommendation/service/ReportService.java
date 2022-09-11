package com.crypto.recommendation.service;

import com.crypto.recommendation.controller.request.CryptoFilterRequest;
import com.crypto.recommendation.controller.response.CryptoStatisticResponse;
import com.crypto.recommendation.dto.CryptoBasicView;
import com.crypto.recommendation.dto.CryptoTopView;
import com.crypto.recommendation.exception.ContentNotFoundException;
import com.crypto.recommendation.repository.BaseRepository;
import com.crypto.recommendation.repository.entity.CryptoHistoryEntity_;
import com.crypto.recommendation.repository.entity.CryptoHistoryEntity;
import com.crypto.recommendation.service.projector.CryptoBasicViewProjector;
import com.crypto.recommendation.service.projector.CryptoStatisticViewProjector;
import com.crypto.recommendation.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

import static com.crypto.recommendation.factory.ResponseStatusEnum.DATA_NOT_FOUND;
import static com.crypto.recommendation.service.mapper.ReportMapper.mapCryptoResponse;
import static com.crypto.recommendation.service.mapper.ReportMapper.mapStatisticResponse;
import static com.crypto.recommendation.service.specification.ReportSpecification.buildSpecification;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {
    private final CryptoBasicViewProjector cryptoBasicViewProjector;
    private final CryptoStatisticViewProjector cryptoStatisticViewProjector;
    private final BaseRepository baseRepository;

    public List<String> getCryptos(final CryptoFilterRequest filterRequest) {
        Specification<CryptoHistoryEntity> filterSpecification = buildSpecification(null, filterRequest);
        Pageable pageable = PageUtil.buildPageRequest(filterRequest.getLimit());

        List<CryptoBasicView> cryptoInBasicViewList = baseRepository.findAll(
                filterSpecification, CryptoHistoryEntity.class, cryptoBasicViewProjector, pageable
        );

        return mapCryptoResponse(cryptoInBasicViewList);
    }

    public CryptoStatisticResponse getCryptoStatistic(final String crypto, CryptoFilterRequest filterRequest) {

        Specification<CryptoHistoryEntity> filterSpecification = buildSpecification(crypto, filterRequest);
        List<CryptoBasicView> cryptoInBasicViewList = baseRepository.findAll(
                filterSpecification, CryptoHistoryEntity.class, cryptoBasicViewProjector, Pageable.unpaged()
        );

        if (cryptoInBasicViewList.isEmpty()) {
            log.error("No data belong to [{}] - request [{}]", crypto, filterRequest);
            throw new ContentNotFoundException(DATA_NOT_FOUND);
        }

        Pageable oldestRecord = PageUtil.buildPageRequest(Sort.Direction.ASC, CryptoHistoryEntity_.timestamp.getName());
        Pageable newestRecord = PageUtil.buildPageRequest(Sort.Direction.DESC, CryptoHistoryEntity_.timestamp.getName());

        BigDecimal oldest = getTopPrice(filterSpecification, oldestRecord);
        BigDecimal newest = getTopPrice(filterSpecification, newestRecord);

        return mapStatisticResponse(cryptoInBasicViewList.get(0), oldest, newest);
    }

    private BigDecimal getTopPrice(final Specification<CryptoHistoryEntity> filterSpecification,
                                   final Pageable pageable) {
        List<CryptoTopView> cryptoTopView = baseRepository.findAll(
                filterSpecification, CryptoHistoryEntity.class, cryptoStatisticViewProjector, pageable
        );
        return cryptoTopView.get(0).getPrice();
    }

}
