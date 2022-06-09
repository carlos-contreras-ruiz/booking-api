package com.github.carloscontrerasruiz.booking.controller;

import com.github.carloscontrerasruiz.booking.dto.*;
import com.github.carloscontrerasruiz.booking.service.interfaces.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping(value = "/checkRoomAvailability", produces = "application/json")
    public ResponseEntity<GeneralResponse<CheckAvailabilityResDto>> checkRoomAvailability(@Valid @RequestBody CheckAvailabilityReqDto request) {
        GeneralResponse<CheckAvailabilityResDto> response = bookingService.checkRoomAvailability(request);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<GeneralResponse<CreateBookingResDto>> createBooking(@Valid @RequestBody CreateBookingReqDto request) {
        GeneralResponse<CreateBookingResDto> response = bookingService.createBooking(request);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
