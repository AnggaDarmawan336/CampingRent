package com.code.camping.utils.dto.webResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Res {
    public static <T> ResponseEntity<?> renderJson(T data, String message, HttpStatus status) {
        WebResponse<T> response = WebResponse.<T>builder()
                .message(message)
                .data(data)
                .build();
        return ResponseEntity.status(status).body(response);
    }
}
