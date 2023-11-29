package com.example.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorException {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm-ss")
    private LocalDateTime timestamp;
    private String error;
    private Integer status;
    private String message;
}
