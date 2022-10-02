package com.example.kakaochannel.advice;

import com.example.kakaochannel.advice.exception.RequestValidIOCException;
import com.example.kakaochannel.advice.exception.RequestValidIllegalCException;
import com.example.kakaochannel.advice.exception.RequestValidNullCException;

import com.example.kakaochannel.service.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class MyAdvice {
    @Autowired
    ResponseService responseService;

    @ExceptionHandler(RequestValidIllegalCException.class)
    protected String throwValidIllegalCException(HttpServletRequest request, RequestValidIllegalCException e) {
        log.info("request: "+request+"\nErrorMessage: "+e.getMessage());
        return responseService.getFailResponse(); //이렇게 반환해도되는지, 카카오톡은 반드시 반환받아야해서
    }
    @ExceptionHandler(RequestValidNullCException.class)
    protected String throwValidNullCException(HttpServletRequest request, RequestValidNullCException e) {
        log.info("request: "+request+"\nErrorMessage: "+e.getMessage());
        return responseService.getFailResponse();
    }
    @ExceptionHandler(RequestValidIOCException.class)
    protected String throwValidIOCException(HttpServletRequest request, RequestValidIOCException e) {
        log.info("request: "+request+"\nErrorMessage: "+e.getMessage());
        return responseService.getFailResponse();
    }
}
