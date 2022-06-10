package com.github.carloscontrerasruiz.booking.repository;

import com.github.carloscontrerasruiz.booking.entity.Booking;
import com.github.carloscontrerasruiz.booking.entity.BookingCancellation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@DataJpaTest
class BookingCancellationRepositoryTest {

    @Autowired
    BookingCancellationRepository repository;

    BookingCancellation entitySaved;

    @BeforeEach
    void setup() {
        final List<BookingCancellation> bookingCancellations = listItems();
        bookingCancellations.stream().forEach((e) -> {
            entitySaved = repository.save(e);
        });
    }

    @Test
    void findAllTest() {
        final List<BookingCancellation> all = repository.findAll();
        Assertions.assertEquals(all.size(), 3);
    }

    @Test
    void findByIdTest() {
        final Optional<BookingCancellation> byId = repository.findById(entitySaved.getId());
        if (byId.isPresent()) {
            Assertions.assertEquals(byId.get().getReason(), entitySaved.getReason());
        }
    }

    @AfterEach
    void cleanUp() {
        repository.deleteAll();
    }


    private List<BookingCancellation> listItems() {
        Date today = new Date();
        return Arrays.asList(
                BookingCancellation.builder()
                        .reason("Test Reason")
                        .cancelledAt(today)
                        .booking(Booking.builder()
                                .arriveDate(today)
                                .leaveDate(today)
                                .createdAt(today)
                                .updatedAt(today)
                                .email("Test@email.com")
                                .isActive(true)
                                .roomNumber(1)
                                .build())

                        .build(),
                BookingCancellation.builder()
                        .reason("Test Reason")
                        .cancelledAt(today)
                        .booking(Booking.builder()
                                .arriveDate(today)
                                .leaveDate(today)
                                .createdAt(today)
                                .updatedAt(today)
                                .email("Test@email.com")
                                .isActive(true)
                                .roomNumber(1)
                                .build())

                        .build(),
                BookingCancellation.builder()
                        .reason("Test Reason")
                        .cancelledAt(today)
                        .booking(Booking.builder()
                                .arriveDate(today)
                                .leaveDate(today)
                                .createdAt(today)
                                .updatedAt(today)
                                .email("Test@email.com")
                                .isActive(true)
                                .roomNumber(1)
                                .build())

                        .build()
        );
    }

}