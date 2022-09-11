package com.crypto.recommendation.controller;

import com.crypto.recommendation.factory.ResponseFactory;
import com.crypto.recommendation.service.CSVService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CryptoController.class)
@SpyBean(classes = ResponseFactory.class)
public class CryptoControllerTest {
    private static final String UPLOAD_CRYPTO_CSV = "/api/crypto/upload";
    private static final String CSV_CONTENT = "timestamp,symbol,price\n1641009600000,BTC,46813.21";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CSVService csvService;

    @Test
    void uploadFile_ValidFile_shouldSuccess() throws Exception {
        MockMultipartFile csvFile =
                new MockMultipartFile("BTC_values.csv", "BTC_values.csv",
                        "text/csv", CSV_CONTENT.getBytes());
        mockMvc.perform(
                        multipart(UPLOAD_CRYPTO_CSV)
                                .file("file", csvFile.getBytes())
                )
                // TODO
//                .andExpect(status().isBadRequest())
                .andDo(print());

    }

    @Test
    void uploadFile_InvalidFile_shouldBadRequest() throws Exception {
        MockMultipartFile csvFile =
                new MockMultipartFile("BTC_values.exe", "BTC_values.exe",
                        "text/csv", CSV_CONTENT.getBytes());
        mockMvc.perform(
                        multipart(UPLOAD_CRYPTO_CSV)
                                .file("file", csvFile.getBytes())
                )
                .andExpect(status().isBadRequest());
    }
}
