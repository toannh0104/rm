package com.crypto.recommendation.service;

import com.crypto.recommendation.controller.request.CryptoFilterRequest;
import com.crypto.recommendation.controller.response.CryptoStatisticResponse;
import com.crypto.recommendation.dto.CryptoBasicView;
import com.crypto.recommendation.dto.CryptoTopView;
import com.crypto.recommendation.exception.ContentNotFoundException;
import com.crypto.recommendation.factory.ResponseStatusEnum;
import com.crypto.recommendation.repository.BaseRepository;
import com.crypto.recommendation.repository.entity.CryptoHistoryEntity;
import com.crypto.recommendation.repository.entity.CryptoHistoryEntity_;
import com.crypto.recommendation.service.projector.CryptoBasicViewProjector;
import com.crypto.recommendation.service.projector.CryptoStatisticViewProjector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

import static com.crypto.recommendation.util.MockUtil.mockAllAttributes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {
    private static final BigDecimal OLDEST = BigDecimal.valueOf(1.02);
    private static final BigDecimal NEWEST = BigDecimal.valueOf(10.12);
    private static final BigDecimal MIN = BigDecimal.valueOf(1.01);
    private static final BigDecimal MAX = BigDecimal.valueOf(10.13);

    @InjectMocks
    private ReportService reportService;

    @Mock
    private CryptoBasicViewProjector cryptoBasicViewProjector;
    @Mock
    private CryptoStatisticViewProjector cryptoStatisticViewProjector;
    @Mock
    private BaseRepository baseRepository;

    @BeforeEach
    void setup() {
        mockAllAttributes(CryptoHistoryEntity_.class);
    }

    @Test
    void getCryptos_returnSuccess() {
        CryptoFilterRequest request = mock(CryptoFilterRequest.class);
        CryptoBasicView statisticBTC = buildCryptoBasicView("BTC");
        CryptoBasicView statisticETC = buildCryptoBasicView("ETC");
        when(request.getLimit()).thenReturn(1);
        when(baseRepository.findAll(
                any(Specification.class), eq(CryptoHistoryEntity.class), eq(cryptoBasicViewProjector), any(Pageable.class)
        )).thenReturn(List.of(statisticBTC, statisticETC));
        reportService.getCryptos(request);
        verify(baseRepository).findAll(
                any(Specification.class), eq(CryptoHistoryEntity.class), eq(cryptoBasicViewProjector), any(Pageable.class)
        );
    }

    @Test
    void getCryptoStatistic_returnSuccess() {
        final String crypto = "BTC";
        CryptoBasicView statistic = buildCryptoBasicView(crypto);

        CryptoTopView cryptoTopView = new CryptoTopView();
        cryptoTopView.setSymbol(crypto);
        cryptoTopView.setPrice(OLDEST);

        CryptoFilterRequest request = mock(CryptoFilterRequest.class);
        when(baseRepository.findAll(
                any(Specification.class), eq(CryptoHistoryEntity.class), eq(cryptoBasicViewProjector), any(Pageable.class)
        )).thenReturn(List.of(statistic));

        when(baseRepository.findAll(
                any(Specification.class), eq(CryptoHistoryEntity.class), eq(cryptoStatisticViewProjector), any(Pageable.class)
        )).thenReturn(List.of(cryptoTopView));

        CryptoStatisticResponse response = reportService.getCryptoStatistic(crypto, request);

        assertNotNull(response);
        assertEquals(OLDEST, response.getOldest());
//        assertEquals(NEWEST, response.getNewest()); //TODO mock order to verify oldest, newest
        assertEquals(MIN, response.getMin());
        assertEquals(MAX, response.getMax());

        verify(baseRepository).findAll(
                any(Specification.class), eq(CryptoHistoryEntity.class), eq(cryptoBasicViewProjector), any(Pageable.class)
        );
        verify(baseRepository, times(2)).findAll(
                any(Specification.class), eq(CryptoHistoryEntity.class), eq(cryptoStatisticViewProjector), any(Pageable.class)
        );
    }

    @Test
    void getCryptoStatistic_WhenNoData_returnBadRequest() {
        final String crypto = "BTC";
        CryptoFilterRequest request = mock(CryptoFilterRequest.class);
        ContentNotFoundException ex = assertThrows(
                ContentNotFoundException.class,
                () -> reportService.getCryptoStatistic(crypto, request)
        );

        assertEquals(ResponseStatusEnum.DATA_NOT_FOUND.getMessage(), ex.getMessage());
        verify(baseRepository).findAll(
                any(Specification.class), eq(CryptoHistoryEntity.class), eq(cryptoBasicViewProjector), any(Pageable.class)
        );
    }

    private CryptoBasicView buildCryptoBasicView(final String crypto) {
        CryptoBasicView statistic = new CryptoBasicView();
        statistic.setSymbol(crypto);
        statistic.setMin(MIN);
        statistic.setMax(MAX);
        return statistic;
    }

}
