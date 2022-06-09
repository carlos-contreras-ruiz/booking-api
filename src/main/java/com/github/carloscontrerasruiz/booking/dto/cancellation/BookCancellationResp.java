package com.github.carloscontrerasruiz.booking.dto.cancellation;

import com.github.carloscontrerasruiz.booking.dto.CreateBookingResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCancellationResp {
    private int id;

    private Date cancelledAt;

    private String reason;

    private CreateBookingResDto book;
}
