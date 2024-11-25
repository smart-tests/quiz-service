package com.smarttest.quizservice.controller;
import com.smarttest.quizservice.util.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity handleServiceException(ServiceException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(exception.getCode()).body(exception.getMessage());
    }
}
