package com.github.carloscontrerasruiz.booking.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "arrive_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date arriveDate;

    @Column(name = "leave_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date leaveDate;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "room_number", nullable = false)
    private int roomNumber;

    @Column(name = "active", nullable = false)
    private boolean isActive;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToOne(mappedBy = "booking")
    private BookingCancellation bookingCancellation;
}
