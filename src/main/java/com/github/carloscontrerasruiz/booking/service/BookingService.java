package com.github.carloscontrerasruiz.booking.service;

import com.github.carloscontrerasruiz.booking.dto.CheckAvailabilityReqDto;
import com.github.carloscontrerasruiz.booking.dto.CheckAvailabilityResDto;
import com.github.carloscontrerasruiz.booking.dto.GeneralResponse;
import com.github.carloscontrerasruiz.booking.entity.Booking;
import com.github.carloscontrerasruiz.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public GeneralResponse<CheckAvailabilityResDto> checkRoomAvailability(CheckAvailabilityReqDto dates) {
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        try {
            Date startDate = formatter.parse(dates.getStartDate());
            Date endDate = formatter.parse(dates.getEndDate());
            final List<Booking> bookings = bookingRepository.findAllByArriveDateBetween(startDate, endDate);
            boolean roomAvailable = bookings.size() > 0 ? false : true;
            return GeneralResponse.<CheckAvailabilityResDto>builder()
                    .httpCode("200")
                    .status(HttpStatus.OK)
                    .body(CheckAvailabilityResDto.builder().roomAvailable(roomAvailable).build())
                    .build();
        } catch (ParseException e) {
            e.printStackTrace();
            return GeneralResponse.<CheckAvailabilityResDto>builder()
                    .httpCode("400")
                    .status(HttpStatus.BAD_REQUEST)
                    .errorMessage("Date format incorrect mm/dd/yyyy")
                    .build();
        }
    }

}
