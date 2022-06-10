package com.github.carloscontrerasruiz.booking.service;

import com.github.carloscontrerasruiz.booking.dto.CreateBookingResDto;
import com.github.carloscontrerasruiz.booking.dto.GeneralResponse;
import com.github.carloscontrerasruiz.booking.dto.cancellation.BookCancellationResp;
import com.github.carloscontrerasruiz.booking.entity.Booking;
import com.github.carloscontrerasruiz.booking.entity.BookingCancellation;
import com.github.carloscontrerasruiz.booking.repository.BookingCancellationRepository;
import com.github.carloscontrerasruiz.booking.repository.BookingRepository;
import com.github.carloscontrerasruiz.booking.service.interfaces.BookingCancellationService;
import com.github.carloscontrerasruiz.booking.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingCancellationServiceImpl implements BookingCancellationService {

    @Autowired
    private BookingCancellationRepository repository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public GeneralResponse<List<BookCancellationResp>> getAllCancellations() {
        final List<BookingCancellation> all = repository.findAll();

        final List<BookCancellationResp> collect = all.stream().map(cancel -> BookCancellationResp.builder()
                .id(cancel.getId())
                .cancelledAt(cancel.getCancelledAt())
                .reason(cancel.getReason())
                .book(CreateBookingResDto.builder()
                        .reservationCode(cancel.getBooking().getId())
                        .arriveDate(cancel.getBooking().getArriveDate().toString())
                        .leaveDate(cancel.getBooking().getLeaveDate().toString())
                        .email(cancel.getBooking().getEmail())
                        .roomNumber(cancel.getBooking().getRoomNumber())
                        .build()
                )
                .build()
        ).collect(Collectors.toList());

        return ResponseUtils.generateGeneralResponse(collect, null, HttpStatus.OK);
    }

    @Override
    public GeneralResponse<BookCancellationResp> getCancellationByCancelId(int id) {
        final Optional<BookingCancellation> optional = repository.findById(id);

        if (!optional.isPresent()) {
            return ResponseUtils.generateGeneralResponse(null,
                    "The cancellation does not exist",
                    HttpStatus.NOT_FOUND);
        }

        BookingCancellation cancellation = optional.get();

        return ResponseUtils.generateGeneralResponse(
                BookCancellationResp.builder()
                        .id(cancellation.getId())
                        .cancelledAt(cancellation.getCancelledAt())
                        .reason(cancellation.getReason())
                        .book(CreateBookingResDto.builder()
                                .reservationCode(cancellation.getBooking().getId())
                                .arriveDate(cancellation.getBooking().getArriveDate().toString())
                                .leaveDate(cancellation.getBooking().getLeaveDate().toString())
                                .email(cancellation.getBooking().getEmail())
                                .roomNumber(cancellation.getBooking().getRoomNumber())
                                .build()
                        )
                        .build(),
                null,
                HttpStatus.OK);
    }

    @Override
    public GeneralResponse<BookCancellationResp> getCancellationByBookId(int id) {
        final Optional<Booking> byBooking = bookingRepository.findById(id);
        if (!byBooking.isPresent()) {
            return ResponseUtils.generateGeneralResponse(null,
                    "The cancellation does not exist",
                    HttpStatus.NOT_FOUND);
        }

        final Booking booking = byBooking.get();

        if (booking.isActive()) {
            return ResponseUtils.generateGeneralResponse(null,
                    "The cancellation does not exist",
                    HttpStatus.NOT_FOUND);
        }

        final BookingCancellation bookingCancellation = booking.getBookingCancellation();
        return ResponseUtils.generateGeneralResponse(
                BookCancellationResp.builder()
                        .id(bookingCancellation.getId())
                        .cancelledAt(bookingCancellation.getCancelledAt())
                        .reason(bookingCancellation.getReason())
                        .book(CreateBookingResDto.builder()
                                .reservationCode(bookingCancellation.getBooking().getId())
                                .arriveDate(bookingCancellation.getBooking().getArriveDate().toString())
                                .leaveDate(bookingCancellation.getBooking().getLeaveDate().toString())
                                .email(bookingCancellation.getBooking().getEmail())
                                .roomNumber(bookingCancellation.getBooking().getRoomNumber())
                                .build()
                        )
                        .build(),
                null,
                HttpStatus.OK);
    }
}
