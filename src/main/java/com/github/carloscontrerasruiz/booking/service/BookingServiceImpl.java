package com.github.carloscontrerasruiz.booking.service;

import com.github.carloscontrerasruiz.booking.dto.*;
import com.github.carloscontrerasruiz.booking.entity.Booking;
import com.github.carloscontrerasruiz.booking.entity.BookingCancellation;
import com.github.carloscontrerasruiz.booking.repository.BookingCancellationRepository;
import com.github.carloscontrerasruiz.booking.repository.BookingRepository;
import com.github.carloscontrerasruiz.booking.service.interfaces.BookingService;
import com.github.carloscontrerasruiz.booking.utils.DateUtils;
import com.github.carloscontrerasruiz.booking.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingCancellationRepository bookingCancellationRepository;

    @Override
    public GeneralResponse<CheckAvailabilityResDto> checkRoomAvailability(CheckAvailabilityReqDto dates) {
        Date startDate = DateUtils.isDateFormatValid(dates.getStartDate());
        Date endDate = DateUtils.isDateFormatValid(dates.getEndDate());

        if (startDate == null || endDate == null) {
            return ResponseUtils.generateGeneralResponse(null,
                    "Date format incorrect mm/dd/yyyy",
                    HttpStatus.BAD_REQUEST);
        }

        //Validate dates
        //In the check availability all the validation dates were included
        //In order to avoid the client frustration with the rules for create the booking
        final String errorMessage = DateUtils.mergeAllValidationDates(startDate, endDate, 3, 30);
        if (errorMessage != null) {
            return ResponseUtils.generateGeneralResponse(null,
                    errorMessage,
                    HttpStatus.BAD_REQUEST);
        }

        return ResponseUtils.generateGeneralResponse(
                CheckAvailabilityResDto.builder()
                        .roomAvailable(isRoomAvailable(startDate, endDate))
                        .build(),
                null,
                HttpStatus.OK);
    }

    @Override
    public GeneralResponse<CreateBookingResDto> createBooking(CreateBookingReqDto booking) {
        Date startDate = DateUtils.isDateFormatValid(booking.getArriveDate());
        Date endDate = DateUtils.isDateFormatValid(booking.getLeaveDate());
        Date today = new Date();
        //Check the format date
        if (startDate == null || endDate == null) {
            return ResponseUtils.generateGeneralResponse(null,
                    "Date format incorrect mm/dd/yyyy",
                    HttpStatus.BAD_REQUEST);
        }
        //Validate dates
        final String errorMessage = DateUtils.mergeAllValidationDates(startDate, endDate, 3, 30);
        if (errorMessage != null) {
            return ResponseUtils.generateGeneralResponse(null,
                    errorMessage,
                    HttpStatus.BAD_REQUEST);
        }

        //Rooms available
        if (!isRoomAvailable(startDate, endDate)) {
            return ResponseUtils.generateGeneralResponse(null,
                    "There are not rooms available for the range",
                    HttpStatus.BAD_REQUEST);
        }

        //Save the booking
        final Booking bookingSaved = this.bookingRepository.save(
                Booking.builder()
                        .arriveDate(startDate)
                        .leaveDate(endDate)
                        .createdAt(today)
                        .updatedAt(today)
                        .email(booking.getEmail())
                        .isActive(true)
                        .roomNumber(1)
                        .build()
        );

        return ResponseUtils.generateGeneralResponse(CreateBookingResDto.builder()
                        .reservationCode(bookingSaved.getId())
                        .arriveDate(bookingSaved.getArriveDate().toString())
                        .leaveDate(bookingSaved.getLeaveDate().toString())
                        .email(bookingSaved.getEmail())
                        .roomNumber(bookingSaved.getRoomNumber())
                        .build(),
                null,
                HttpStatus.CREATED);
    }

    @Override
    public GeneralResponse<CreateBookingResDto> updateBooking(UpdateBookingReqDto request, int idBook) {
        Date startDate = DateUtils.isDateFormatValid(request.getArriveDate());
        Date endDate = DateUtils.isDateFormatValid(request.getLeaveDate());
        Date today = new Date();
        //Check the format date
        if (startDate == null || endDate == null) {
            return ResponseUtils.generateGeneralResponse(null,
                    "Date format incorrect mm/dd/yyyy",
                    HttpStatus.BAD_REQUEST);
        }
        //Validate dates
        final String errorMessage = DateUtils.mergeAllValidationDates(startDate, endDate, 3, 30);
        if (errorMessage != null) {
            return ResponseUtils.generateGeneralResponse(null,
                    errorMessage,
                    HttpStatus.BAD_REQUEST);
        }

        //Rooms available
        if (!isRoomAvailable(startDate, endDate)) {
            return ResponseUtils.generateGeneralResponse(null,
                    "There are not rooms available for the range",
                    HttpStatus.BAD_REQUEST);
        }

        final Optional<Booking> bookOptional = bookingRepository.findById(idBook);
        if (!bookOptional.isPresent()) {
            return ResponseUtils.generateGeneralResponse(null,
                    "The booking not exist",
                    HttpStatus.BAD_REQUEST);
        }

        Booking booking = bookOptional.get();
        booking.setArriveDate(startDate);
        booking.setLeaveDate(endDate);
        booking.setUpdatedAt(today);
        if (request.getEmail() != null || !request.getEmail().isEmpty()) booking.setEmail(request.getEmail());
        //Save the booking
        booking = bookingRepository.save(booking);

        return ResponseUtils.generateGeneralResponse(CreateBookingResDto.builder()
                        .reservationCode(booking.getId())
                        .arriveDate(booking.getArriveDate().toString())
                        .leaveDate(booking.getLeaveDate().toString())
                        .email(booking.getEmail())
                        .roomNumber(booking.getRoomNumber())
                        .build(),
                null,
                HttpStatus.OK);
    }

    @Override
    public GeneralResponse<CheckAvailabilityResDto> deleteBooking(DeleteBookingReqDto request, int idBook) {
        final Optional<Booking> bookOptional = bookingRepository.findById(idBook);
        if (!bookOptional.isPresent()) {
            return ResponseUtils.generateGeneralResponse(null,
                    "The booking not exist",
                    HttpStatus.BAD_REQUEST);
        }

        Booking booking = bookOptional.get();
        booking.setActive(false);
        bookingRepository.save(booking);

        bookingCancellationRepository.save(BookingCancellation.builder()
                .cancelledAt(new Date())
                .booking(booking)
                .reason(
                        request.getReason().isEmpty() ? "" : request.getReason()
                )
                .build()
        );

        return ResponseUtils.generateGeneralResponse(
                null,
                null,
                HttpStatus.OK);
    }

    @Override
    public GeneralResponse<CreateBookingResDto> getBookingById(int idBook) {

        final Optional<Booking> bookOptional = bookingRepository.findById(idBook);
        if (!bookOptional.isPresent()) {
            return ResponseUtils.generateGeneralResponse(null,
                    "The booking not exist",
                    HttpStatus.BAD_REQUEST);
        }

        Booking booking = bookOptional.get();
        return ResponseUtils.generateGeneralResponse(CreateBookingResDto.builder()
                        .reservationCode(booking.getId())
                        .arriveDate(booking.getArriveDate().toString())
                        .leaveDate(booking.getLeaveDate().toString())
                        .email(booking.getEmail())
                        .roomNumber(booking.getRoomNumber())
                        .build(),
                null,
                HttpStatus.OK);
    }

    @Override
    public GeneralResponse<List<CreateBookingResDto>> getAllBookings() {
        final List<Booking> bookings = bookingRepository.findByIsActive(true);

        final List<CreateBookingResDto> respList = bookings.stream().map((booking) -> CreateBookingResDto.builder()
                .reservationCode(booking.getId())
                .arriveDate(booking.getArriveDate().toString())
                .leaveDate(booking.getLeaveDate().toString())
                .email(booking.getEmail())
                .roomNumber(booking.getRoomNumber())
                .build()
        ).collect(Collectors.toList());

        return ResponseUtils.generateGeneralResponse(respList, null, HttpStatus.OK);
    }

    private boolean isRoomAvailable(Date startDate, Date endDate) {
        final List<Booking> bookings = bookingRepository.findAvailability(startDate, endDate);
        return bookings.isEmpty();
    }
}
