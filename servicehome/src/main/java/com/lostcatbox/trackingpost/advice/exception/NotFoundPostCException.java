package com.lostcatbox.trackingpost.advice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundPostCException extends RuntimeException{
    public NotFoundPostCException(String message) {
        super(message);
    }
}
