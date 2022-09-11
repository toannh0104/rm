package com.crypto.recommendation.exception;

import com.crypto.recommendation.factory.ResponseFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.crypto.recommendation.factory.ResponseStatusEnum.FILE_SIZE_TOO_LARGE;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

  @Autowired
  private ResponseFactory responseFactory;
  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity handleMaxSizeException(MaxUploadSizeExceededException ex) {
    log.error("MaxUploadSizeExceededException", ex);
    return responseFactory.error(HttpStatus.EXPECTATION_FAILED, FILE_SIZE_TOO_LARGE);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity handleException(Exception e) {
    log.error("Exception", e);
    return responseFactory.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
  }

  @ExceptionHandler(ContentNotFoundException.class)
  public ResponseEntity handleContentNotFoundException(ContentNotFoundException e) {
    log.error("ContentNotFoundException", e);
    return responseFactory.error(HttpStatus.BAD_REQUEST, e.getMessage());
  }


}