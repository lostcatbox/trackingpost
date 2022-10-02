package com.example.externalpost.advice;

import com.example.externalpost.advice.exception.CrawlingNullCException;
import com.example.externalpost.advice.exception.NotSupportProviderCException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class MyAdvice {
    @ExceptionHandler(CrawlingNullCException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected void nullatCrawlingCException(HttpServletRequest request, CrawlingNullCException e) {
        log.info("requestURI: "+request.getRequestURI()+"\nErrorMessage: "+e.getMessage());
    }
    @ExceptionHandler(NotSupportProviderCException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected void canNotFoundProvider(HttpServletRequest request, NotSupportProviderCException e) {
        log.info("requestURI: "+request.getRequestURI()+"\nErrorMessage: "+e.getMessage());
    }

}

