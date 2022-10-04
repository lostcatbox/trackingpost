package com.example.kakaochannel.advice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RequestValidIOCException extends RuntimeException{
    public RequestValidIOCException(String message) {
        super(message);
    }
}
