package com.github.carloscontrerasruiz.booking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralResponse<T> {
    private String httpCode;
    @JsonIgnore
    private HttpStatus status;
    private T body;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMessage;
}
