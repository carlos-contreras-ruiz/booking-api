package com.github.carloscontrerasruiz.booking.exception;

import com.github.carloscontrerasruiz.booking.dto.CreateBookingReqDto;
import com.github.carloscontrerasruiz.booking.dto.CreateBookingResDto;
import com.github.carloscontrerasruiz.booking.dto.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    //handle Method argument validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralResponse<CreateBookingResDto>> handleValidException(MethodArgumentNotValidException exception) {
        final String errorMessage = exception.getBindingResult().getFieldError() != null ?
                exception.getBindingResult().getFieldError().getDefaultMessage() : "Invalid field";

        final GeneralResponse response = GeneralResponse.<CreateBookingResDto>builder()
                .httpCode("400")
                .errorMessage(errorMessage)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //handle Global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralResponse<CreateBookingReqDto>> handleGlobalException(Exception exception) {
        final GeneralResponse response = GeneralResponse.<CreateBookingResDto>builder()
                .httpCode("500")
                .errorMessage(exception.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
