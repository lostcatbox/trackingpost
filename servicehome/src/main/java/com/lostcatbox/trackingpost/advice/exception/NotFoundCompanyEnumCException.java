package com.lostcatbox.trackingpost.advice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundCompanyEnumCException extends RuntimeException{
    public NotFoundCompanyEnumCException(String message) {
        super(message);
    }
}
