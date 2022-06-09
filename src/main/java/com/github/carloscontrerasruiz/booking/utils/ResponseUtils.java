package com.github.carloscontrerasruiz.booking.utils;

import com.github.carloscontrerasruiz.booking.dto.GeneralResponse;
import org.springframework.http.HttpStatus;

public class ResponseUtils {

    public static <E> GeneralResponse<E> generateGeneralResponse(E body, String errorMessage, HttpStatus status) {
        return GeneralResponse.<E>builder()
                .httpCode(Integer.toString(status.value()))
                .status(status)
                .body(body)
                .errorMessage(errorMessage)
                .build();
    }
}
