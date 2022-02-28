package com.example.classs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class UnauthorizedException extends HttpStatusCodeException {

    public UnauthorizedException(HttpStatus statusCode) {
        super(statusCode);
    }
}
