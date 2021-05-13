package com.example.task1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDTO {

    private String message;
    private boolean success;
    private Object object;

    public ResponseDTO(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ResponseDTO(String message, boolean success, Object object) {
        this.message = message;
        this.success = success;
        this.object = object;
    }
}
