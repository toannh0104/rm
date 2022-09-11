package com.crypto.recommendation.service.mapper;

import com.crypto.recommendation.controller.response.CryptoStatisticResponse;
import com.crypto.recommendation.dto.CryptoBasicView;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@UtilityClass
public class ReportMapper {
    public static CryptoStatisticResponse mapStatisticResponse(
            final CryptoBasicView cryptoBasicView,
            final BigDecimal oldest,
            final BigDecimal newest
    ) {
        CryptoStatisticResponse statisticResponse = new CryptoStatisticResponse();
        statisticResponse.setMin(cryptoBasicView.getMin());
        statisticResponse.setMax(cryptoBasicView.getMax());
        statisticResponse.setOldest(oldest);
        statisticResponse.setNewest(newest);
        return statisticResponse;
    }

    public static List<String> mapCryptoResponse(final List<CryptoBasicView> cryptoInBasicViewList) {
        Comparator<CryptoBasicView> sortByFormula = (obj1, obj2) ->
                Long.compare(computeCompareValue(obj2), computeCompareValue(obj1));
        return cryptoInBasicViewList.stream()
                .sorted(sortByFormula)
                .map(CryptoBasicView::getSymbol)
                .collect(toUnmodifiableList());
    }

    private Long computeCompareValue(final CryptoBasicView obj) {
        return (obj.getMax().subtract(obj.getMin()))
                .divide(obj.getMin(), 2, RoundingMode.HALF_UP)
                .toBigInteger()
                .longValue();
    }
}
