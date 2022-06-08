package com.github.carloscontrerasruiz.booking.repository;

import com.github.carloscontrerasruiz.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    //    List<Booking> findAllByArriveDateBetweenOrLeaveDateBetween(
//            Date startDate,
//            Date endDate);
    List<Booking> findAllByArriveDateBetween(
            Date startDate,
            Date endDate);
}
