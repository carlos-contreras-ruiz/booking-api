package com.github.carloscontrerasruiz.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteBookingReqDto {
    @NotNull(message = "The reason is required")
    @NotBlank(message = "The reason is required")
    private String reason;
}
