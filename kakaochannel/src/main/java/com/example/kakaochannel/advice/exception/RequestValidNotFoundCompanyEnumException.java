package com.example.kakaochannel.advice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RequestValidNotFoundCompanyEnumException extends RuntimeException{
    public RequestValidNotFoundCompanyEnumException(String message) {
        super(message);
    }
}
