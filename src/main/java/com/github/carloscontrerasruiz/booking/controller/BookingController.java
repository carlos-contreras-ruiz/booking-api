package com.github.carloscontrerasruiz.booking.controller;

import com.github.carloscontrerasruiz.booking.dto.*;
import com.github.carloscontrerasruiz.booking.service.interfaces.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<GeneralResponse<List<CreateBookingResDto>>> getAllBookings() {
        GeneralResponse<List<CreateBookingResDto>> response = bookingService.getAllBookings();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<GeneralResponse<CreateBookingResDto>> getBookingById(@PathVariable int id) {
        GeneralResponse<CreateBookingResDto> response = bookingService.getBookingById(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<GeneralResponse<CreateBookingResDto>> updateBooking(@Valid @RequestBody UpdateBookingReqDto request, @PathVariable int id) {
        GeneralResponse<CreateBookingResDto> response = bookingService.updateBooking(request, id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<GeneralResponse<CheckAvailabilityResDto>> deleteBooking(@Valid @RequestBody DeleteBookingReqDto request, @PathVariable int id) {
        GeneralResponse<CheckAvailabilityResDto> response = bookingService.deleteBooking(request, id);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
