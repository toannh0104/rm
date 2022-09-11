package com.crypto.recommendation.service;

import com.crypto.recommendation.repository.CryptoHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CSVServiceTest {
    private static final String CSV_CONTENT = "timestamp,symbol,price\n1641009600000,BTC,46813.21";
    @InjectMocks
    private CSVService csvService;

    @Mock
    private CryptoHistoryRepository cryptoHistoryRepository;

    @Test
    void save_validUpload_returnSuccess() throws IOException {
        MockMultipartFile file = new MockMultipartFile("BTC_values.csv", CSV_CONTENT.getBytes(StandardCharsets.UTF_8));
        csvService.save(file);
        verify(cryptoHistoryRepository).saveAll(anyList());
    }
}
