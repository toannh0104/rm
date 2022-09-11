package com.crypto.recommendation.controller;

import com.crypto.recommendation.controller.request.CryptoFilterRequest;
import com.crypto.recommendation.controller.response.CryptoStatisticResponse;
import com.crypto.recommendation.factory.ResponseFactory;
import com.crypto.recommendation.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final ResponseFactory responseFactory;

    @GetMapping("/cryptos")
    public ResponseEntity<List<String>> getCryptos(
            @RequestParam(value = "fromTimestamp", required = false) final LocalDateTime fromTimestamp,
            @RequestParam(value = "toTimestamp", required = false) final LocalDateTime toTimestamp,
            @RequestParam(value = "limit", required = false) final Integer limit
    ) {
        CryptoFilterRequest cryptoFilterRequest = buildCryptoFilterRequest(limit, fromTimestamp, toTimestamp);
        List<String> currencies = reportService.getCryptos(cryptoFilterRequest);
        return responseFactory.success(currencies, List.class);
    }

    @GetMapping("/{crypto}/statistics")
    public ResponseEntity<CryptoStatisticResponse> getStatistics(
            @PathVariable("crypto") final String crypto,
            @RequestParam(value = "fromTimestamp", required = false) final LocalDateTime fromTimestamp,
            @RequestParam(value = "toTimestamp", required = false) final LocalDateTime toTimestamp
    ) {
        final CryptoFilterRequest cryptoFilterRequest = buildCryptoFilterRequest(null, fromTimestamp, toTimestamp);

        CryptoStatisticResponse cryptoStatisticResponses = reportService.getCryptoStatistic(crypto, cryptoFilterRequest);
        return responseFactory.success(cryptoStatisticResponses, CryptoStatisticResponse.class);
    }

    private CryptoFilterRequest buildCryptoFilterRequest(
            final Integer limit,
            final LocalDateTime fromTimestamp,
            final LocalDateTime toTimestamp
    ) {
        CryptoFilterRequest cryptoFilterRequest = new CryptoFilterRequest();
        cryptoFilterRequest.setLimit(limit);
        cryptoFilterRequest.setFromTimestamp(fromTimestamp);
        cryptoFilterRequest.setToTimestamp(toTimestamp);
        return cryptoFilterRequest;
    }

}