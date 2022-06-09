package com.github.carloscontrerasruiz.booking.repository;

import com.github.carloscontrerasruiz.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("SELECT b FROM Booking b WHERE b.arriveDate BETWEEN :startDate AND :endDate OR b.leaveDate BETWEEN :startDate AND :endDate")
    List<Booking> findAvailability(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );
}
