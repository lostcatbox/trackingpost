package com.example.kakaochannel.advice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RequestValidIllegalCException extends RuntimeException{
    public RequestValidIllegalCException(String message){
        super(message);
    }
}
