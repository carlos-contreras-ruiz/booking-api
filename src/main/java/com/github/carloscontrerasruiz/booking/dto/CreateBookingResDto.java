package com.github.carloscontrerasruiz.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingResDto {
    private int reservationCode;
    private String arriveDate;
    private String leaveDate;
    private String name;
    private int roomNumber;
}
