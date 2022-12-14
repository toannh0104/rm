package com.crypto.recommendation.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import com.crypto.recommendation.repository.entity.CryptoHistoryEntity;
import lombok.experimental.UtilityClass;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

@UtilityClass
public class CSVHelper {
    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<CryptoHistoryEntity> csvToTutorials(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<CryptoHistoryEntity> priceHistories = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                CryptoHistoryEntity tutorial = new CryptoHistoryEntity(
                        LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(csvRecord.get("timestamp"))),
                                TimeZone.getDefault().toZoneId()),
                        csvRecord.get("symbol"),
                        new BigDecimal(csvRecord.get("price"))
                );
                priceHistories.add(tutorial);
            }
            return priceHistories;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}