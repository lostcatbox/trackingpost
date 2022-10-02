package com.example.externalpost.advice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotSupportProviderCException extends RuntimeException {
    public NotSupportProviderCException(String message){
        super(message);
    }
}
