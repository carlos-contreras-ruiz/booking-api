package com.github.carloscontrerasruiz.booking.repository;

import com.github.carloscontrerasruiz.booking.entity.BookingCancellation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingCancellationRepository extends JpaRepository<BookingCancellation, Integer> {
    List<BookingCancellation> findByBooking(int id);
}
