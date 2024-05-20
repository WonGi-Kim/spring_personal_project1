package com.sparta.springpersonalproject1.dto;

import lombok.NoArgsConstructor;


public class CustomResponse<T> {
    private int code;
    private String message;
    private T body;

    public CustomResponse() {

    }

    public void setCode(int statusCode) {
        this.code = statusCode;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setBody(T body) {
        this.body = body;
    }

    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
    public T getBody() {
        return body;
    }
}
