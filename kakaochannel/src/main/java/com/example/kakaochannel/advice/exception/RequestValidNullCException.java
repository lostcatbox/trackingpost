package com.example.kakaochannel.advice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RequestValidNullCException extends RuntimeException{
    public RequestValidNullCException(String message) {
        super(message);
    }
}
