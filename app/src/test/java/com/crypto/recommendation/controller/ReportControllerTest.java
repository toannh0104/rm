package com.crypto.recommendation.controller;

import com.crypto.recommendation.controller.request.CryptoFilterRequest;
import com.crypto.recommendation.controller.response.CryptoStatisticResponse;
import com.crypto.recommendation.factory.ResponseFactory;
import com.crypto.recommendation.factory.ResponseStatusEnum;
import com.crypto.recommendation.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@WebMvcTest(ReportController.class)
@SpyBean(classes = ResponseFactory.class)
public class ReportControllerTest {
    private static final String REPORT_CRYPTOS = "/api/report/cryptos";
    private static final String REPORT_CRYPTOS_STATISTICS = "/api/report/{crypto}/statistics";

    private static final BigDecimal OLDEST = BigDecimal.valueOf(1.02);
    private static final BigDecimal NEWEST = BigDecimal.valueOf(10.12);
    private static final BigDecimal MIN = BigDecimal.valueOf(1.01);
    private static final BigDecimal MAX = BigDecimal.valueOf(10.13);

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReportService reportService;

    @Test
    void reportCrypto_shouldSuccess() throws Exception {
        List<String> data = List.of("BTC", "ETC");
        when(reportService.getCryptos(any(CryptoFilterRequest.class))).thenReturn(data);
        mockMvc.perform(get(REPORT_CRYPTOS))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.code", is(ResponseStatusEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.status.code", is(ResponseStatusEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.data", is(data)));
        verify(reportService).getCryptos(any(CryptoFilterRequest.class));
    }

    @Test
    void reportStatistics_shouldSuccess() throws Exception {
        final String crypto = "BTC";
        CryptoStatisticResponse response = buildCryptoStatisticResponse();
        when(reportService.getCryptoStatistic(eq(crypto), any(CryptoFilterRequest.class))).thenReturn(response);
        mockMvc.perform(get(REPORT_CRYPTOS_STATISTICS, crypto))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.status.code", is(ResponseStatusEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.status.code", is(ResponseStatusEnum.SUCCESS.getCode())))
                .andExpect(jsonPath("$.data.oldest", is(OLDEST.doubleValue())))
                .andExpect(jsonPath("$.data.newest", is(NEWEST.doubleValue())))
                .andExpect(jsonPath("$.data.min", is(MIN.doubleValue())))
                .andExpect(jsonPath("$.data.max", is(MAX.doubleValue())));
        verify(reportService).getCryptoStatistic(eq(crypto), any(CryptoFilterRequest.class));
    }

    private CryptoStatisticResponse buildCryptoStatisticResponse() {
        CryptoStatisticResponse response = new CryptoStatisticResponse();
        response.setOldest(OLDEST);
        response.setNewest(NEWEST);
        response.setMax(MAX);
        response.setMin(MIN);
        return response;
    }


}
