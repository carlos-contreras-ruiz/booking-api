package com.github.carloscontrerasruiz.booking.service;

import com.github.carloscontrerasruiz.booking.constants.Constants;
import com.github.carloscontrerasruiz.booking.dto.*;
import com.github.carloscontrerasruiz.booking.entity.Booking;
import com.github.carloscontrerasruiz.booking.repository.BookingCancellationRepository;
import com.github.carloscontrerasruiz.booking.repository.BookingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.*;

class BookingServiceImplTest {

    @Mock
    BookingRepository bookingRepository;

    @Mock
    BookingCancellationRepository bookingCancellationRepository;

    @InjectMocks
    BookingServiceImpl service;

    private AutoCloseable closeable;

    @BeforeEach
    void initService() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    void checkRoomAvailability() {
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Mockito.doReturn(listItems()).when(bookingRepository).findAvailability(Mockito.any(), Mockito.any());

        final GeneralResponse<CheckAvailabilityResDto> response = service.checkRoomAvailability(
                CheckAvailabilityReqDto.builder()
                        .startDate(formatter.format(today))
                        .endDate(formatter.format(today))
                        .build()
        );

        Assertions.assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST);
        Assertions.assertEquals(response.getErrorMessage(), Constants.BOOKING_ONE_DAY_AFTER);
    }

    @Test
    void deleteBookingTest() {
        Date today = new Date();
        Booking book = Booking.builder()
                .arriveDate(today)
                .leaveDate(today)
                .createdAt(today)
                .updatedAt(today)
                .email("Test@email.com")
                .isActive(true)
                .roomNumber(1)
                .build();

        Mockito.when(bookingRepository.findByIdAndIsActive(1, true)).thenReturn(Optional.of(book));
        Mockito.doReturn(null).when(bookingRepository).save(Mockito.any());
        Mockito.doReturn(null).when(bookingCancellationRepository).save(Mockito.any());

        GeneralResponse<CheckAvailabilityResDto> resp = service.deleteBooking(DeleteBookingReqDto.builder()
                .reason("The reason")
                .build(), 1);

        Assertions.assertEquals(resp.getStatus(), HttpStatus.OK);

        Mockito.when(bookingRepository.findByIdAndIsActive(1, true)).thenReturn(Optional.empty());

        resp = service.deleteBooking(DeleteBookingReqDto.builder()
                .reason("The reason")
                .build(), 1);
        Assertions.assertEquals(resp.getStatus(), HttpStatus.NOT_FOUND);
        Assertions.assertEquals(resp.getErrorMessage(), Constants.BOOKING_NOT_EXIST);
    }

    @Test
    void getBookingByIdTest() {
        Date today = new Date();
        Booking book = Booking.builder()
                .arriveDate(today)
                .leaveDate(today)
                .createdAt(today)
                .updatedAt(today)
                .email("Test@email.com")
                .isActive(true)
                .roomNumber(1)
                .build();

        Mockito.when(bookingRepository.findById(1)).thenReturn(Optional.of(book));

        GeneralResponse<CreateBookingResDto> resp = service.getBookingById(1);

        Assertions.assertEquals(resp.getStatus(), HttpStatus.OK);

        Mockito.when(bookingRepository.findById(1)).thenReturn(Optional.empty());
        resp = service.getBookingById(1);

        Assertions.assertEquals(resp.getStatus(), HttpStatus.NOT_FOUND);
        Assertions.assertEquals(resp.getErrorMessage(), Constants.BOOKING_NOT_EXIST);
    }

    @Test
    void getAllBookings() {
        Mockito.when(bookingRepository.findByIsActive(true)).thenReturn(listItems());
        final GeneralResponse<List<CreateBookingResDto>> resp = service.getAllBookings();

        Assertions.assertEquals(resp.getStatus(), HttpStatus.OK);
        Assertions.assertEquals(resp.getBody().size(), 3);
    }


    private List<Booking> listItems() {
        Date today = new Date();

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 2);
        dt = c.getTime();

        return Arrays.asList(
                Booking.builder()
                        .arriveDate(today)
                        .leaveDate(dt)
                        .createdAt(today)
                        .updatedAt(today)
                        .email("Test@email.com")
                        .isActive(true)
                        .roomNumber(1)
                        .build(),
                Booking.builder()
                        .arriveDate(today)
                        .leaveDate(dt)
                        .createdAt(today)
                        .updatedAt(today)
                        .email("Test@email.com")
                        .isActive(false)
                        .roomNumber(1)
                        .build(),
                Booking.builder()
                        .arriveDate(today)
                        .leaveDate(today)
                        .createdAt(today)
                        .updatedAt(today)
                        .email("Test@email.com")
                        .isActive(true)
                        .roomNumber(1)
                        .build()
        );
    }
}