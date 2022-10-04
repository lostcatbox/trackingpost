package com.example.externalpost.advice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CrawlingNullCException extends RuntimeException{
    public CrawlingNullCException(String message) {
        super(message);
    }
}
