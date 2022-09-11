package com.crypto.recommendation.factory;

import com.crypto.recommendation.dto.GeneralResponse;
import com.crypto.recommendation.dto.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseFactory {
    public ResponseEntity success(Object data, Class clazz) {
        GeneralResponse generalResponse = new GeneralResponse();
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setCode(ResponseStatusEnum.SUCCESS.getCode());
        responseStatus.setMessage(ResponseStatusEnum.SUCCESS.getMessage());
        generalResponse.setStatus(responseStatus);
        generalResponse.setData(clazz.cast(data));
        return ResponseEntity.ok(generalResponse);
    }

    public ResponseEntity error(HttpStatus httpStatus, ResponseStatusEnum errorStatus) {
        GeneralResponse generalResponse = new GeneralResponse();
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setCode(errorStatus.getCode());
        responseStatus.setMessage(errorStatus.getMessage());
        generalResponse.setStatus(responseStatus);
        return ResponseEntity.status(httpStatus).body(generalResponse);
    }

    public ResponseEntity error(HttpStatus httpStatus, String message) {
        GeneralResponse generalResponse = new GeneralResponse();
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setMessage(message);
        generalResponse.setStatus(responseStatus);
        return ResponseEntity.status(httpStatus).body(generalResponse);
    }
}
