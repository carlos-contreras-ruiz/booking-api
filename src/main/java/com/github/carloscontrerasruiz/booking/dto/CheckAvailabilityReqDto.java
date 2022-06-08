package com.github.carloscontrerasruiz.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckAvailabilityReqDto {

    @NotNull(message = "Start date required")
    @NotBlank(message = "Start date required")
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Date format incorrect mm/dd/yyyy")
    private String startDate;

    @NotNull(message = "End date required")
    @NotBlank(message = "End date required")
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Date format incorrect mm/dd/yyyy")
    private String endDate;
}
