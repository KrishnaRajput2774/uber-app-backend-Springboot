package com.rk.uberApp.advices;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError{

    private String message;
    private HttpStatus status;
    private List<String> subErrors;

}
