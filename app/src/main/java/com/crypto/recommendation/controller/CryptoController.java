package com.crypto.recommendation.controller;

import com.crypto.recommendation.factory.ResponseFactory;
import com.crypto.recommendation.service.CSVService;
import com.crypto.recommendation.util.CSVHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.crypto.recommendation.factory.ResponseStatusEnum.UNSUPPORTED_FILE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/crypto")
public class CryptoController {
    private final CSVService fileService;
    private final ResponseFactory responseFactory;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String message = "";
        if (CSVHelper.hasCSVFormat(file)) {
            fileService.save(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return responseFactory.success(message, String.class);
        }
        return responseFactory.error(HttpStatus.BAD_REQUEST, UNSUPPORTED_FILE);
    }

}