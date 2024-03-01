package com.shop.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class HandleExceptionCustom extends RuntimeException {
	String message;
    public HandleExceptionCustom(String message) {
        super();
        this.message=message;
    }
    public String getMessage() {
		return message;
	}
}
