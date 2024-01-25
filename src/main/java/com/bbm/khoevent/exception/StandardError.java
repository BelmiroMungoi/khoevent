package com.bbm.khoevent.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class StandardError {

    private int code;
    private HttpStatus status;
    private LocalDateTime time;
    private String title;
    private String path;
    private List<FieldError> fields;

    @Data
    @AllArgsConstructor
    public static class FieldError {
        private String fieldName;
        private String message;
    }
}
