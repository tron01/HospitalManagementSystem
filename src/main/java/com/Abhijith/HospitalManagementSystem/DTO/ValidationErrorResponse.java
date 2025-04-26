package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ValidationErrorResponse {
    private String timestamp;
    private int status;
    private String error;
    private List<FieldError> errors; // Field-specific errors
}
