package com.sparta.springpersonalproject1.dto;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomResponse<T> {
    private int code;
    private String message;
    private T body;

    public CustomResponse() {
    }

    public static <T> CustomResponse<T> makeResponse(T data, HttpStatus status) {
        CustomResponse<T> customResponse = new CustomResponse<>();
        customResponse.setCode(status.value());
        customResponse.setMessage(status.getReasonPhrase());
        customResponse.setBody(data);

        return customResponse;
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
