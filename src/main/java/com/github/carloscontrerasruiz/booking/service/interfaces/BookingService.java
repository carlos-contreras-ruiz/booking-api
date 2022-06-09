package com.github.carloscontrerasruiz.booking.service.interfaces;

import com.github.carloscontrerasruiz.booking.dto.*;

public interface BookingService {
    GeneralResponse<CheckAvailabilityResDto> checkRoomAvailability(CheckAvailabilityReqDto dates);

    GeneralResponse<CreateBookingResDto> createBooking(CreateBookingReqDto booking);
}
