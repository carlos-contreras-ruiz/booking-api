package com.github.carloscontrerasruiz.booking.service.interfaces;

import com.github.carloscontrerasruiz.booking.dto.*;

import java.util.List;

public interface BookingService {
    GeneralResponse<CheckAvailabilityResDto> checkRoomAvailability(CheckAvailabilityReqDto dates);

    GeneralResponse<CreateBookingResDto> createBooking(CreateBookingReqDto booking);

    GeneralResponse<CreateBookingResDto> updateBooking(UpdateBookingReqDto booking, int idBook);

    GeneralResponse<CheckAvailabilityResDto> deleteBooking(DeleteBookingReqDto request, int idBook);

    GeneralResponse<CreateBookingResDto> getBookingById(int idBook);

    GeneralResponse<List<CreateBookingResDto>> getAllBookings();
}
