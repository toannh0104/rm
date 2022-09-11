package com.crypto.recommendation.service;

import java.io.IOException;
import java.util.List;

import com.crypto.recommendation.repository.CryptoHistoryRepository;
import com.crypto.recommendation.repository.entity.CryptoHistoryEntity;
import com.crypto.recommendation.util.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVService {
    @Autowired
    private CryptoHistoryRepository repository;

    public void save(MultipartFile file) throws IOException {
            List<CryptoHistoryEntity> priceHistories = CSVHelper.csvToTutorials(file.getInputStream());
            repository.saveAll(priceHistories);
    }
}