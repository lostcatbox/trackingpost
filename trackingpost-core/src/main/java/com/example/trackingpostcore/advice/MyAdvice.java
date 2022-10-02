package com.example.trackingpostcore.advice;

import com.example.trackingpostcore.advice.exception.NotFoundCompanyEnumCException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class MyAdvice {
    //core에있어도. Runtimeerror가 가능한지? 테스트 필요
    @ExceptionHandler(NotFoundCompanyEnumCException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected void notFoundCompanyEnumCException(HttpServletRequest request, NotFoundCompanyEnumCException e) {
        log.info("request: "+request+"\nErrorMessage: "+e.getMessage());
    }
}
