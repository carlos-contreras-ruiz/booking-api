package com.github.carloscontrerasruiz.booking.repository;

import com.github.carloscontrerasruiz.booking.entity.Booking;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

@DataJpaTest
class BookingRepositoryTest {

    @Autowired
    private BookingRepository repository;
    Booking entitySaved;

    @BeforeEach
    void setup() {
        final List<Booking> bookings = listItems();
        bookings.stream().forEach((e) -> {
            entitySaved = repository.save(e);
        });
    }

    @Test
    void findByIsActiveTest() {
        List<Booking> bookings = repository.findByIsActive(true);
        Assertions.assertEquals(bookings.size(), 2);

        bookings = repository.findByIsActive(false);
        Assertions.assertEquals(bookings.size(), 1);
    }

    @Test
    void findByIdAndIsActiveTest() {
        Optional<Booking> book = repository.findByIdAndIsActive(entitySaved.getId(), entitySaved.isActive());
        Assertions.assertTrue(book.isPresent());

        book = repository.findByIdAndIsActive(entitySaved.getId(), !entitySaved.isActive());
        Assertions.assertFalse(book.isPresent());
    }

    @AfterEach
    void cleanUp() {
        repository.deleteAll();
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