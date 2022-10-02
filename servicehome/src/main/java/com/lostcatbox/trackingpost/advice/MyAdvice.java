package com.lostcatbox.trackingpost.advice;

import com.lostcatbox.trackingpost.advice.exception.NotFoundCompanyEnumCException;
import com.lostcatbox.trackingpost.advice.exception.NotFoundPostCException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class MyAdvice {
    @ExceptionHandler(NotFoundPostCException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected void NotFoundPostCException(HttpServletRequest request, NotFoundPostCException e) {
        log.info("request: "+request+"\nErrorMessage: "+e.getMessage());
    }
    @ExceptionHandler(NotFoundCompanyEnumCException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected void notFoundCompanyEnumCException(HttpServletRequest request, NotFoundCompanyEnumCException e) {
        log.info("request: "+request+"\nErrorMessage: "+e.getMessage());
    }
}
