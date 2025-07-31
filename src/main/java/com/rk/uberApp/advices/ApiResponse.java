package com.rk.uberApp.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiResponse<T>{

    @JsonFormat(pattern ="HH:mm:ss dd-MM-yyyy")
    private LocalDateTime timeStamp;

    private T data;

    private ApiError apiError;


    public ApiResponse() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(T data) {
        this();
        this.data = data;
    }

    public ApiResponse(ApiError apiError) {
        this();
        this.apiError = apiError;
    }
}
