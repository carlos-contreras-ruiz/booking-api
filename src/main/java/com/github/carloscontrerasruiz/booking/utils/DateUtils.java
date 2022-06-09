package com.github.carloscontrerasruiz.booking.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class DateUtils {

    public static Date isDateFormatValid(String date) {
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        formatter.setLenient(false);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isStartBeforeEnd(Date startDate, Date endDate) {
        return !startDate.after(endDate);
    }

    public static boolean isTheRangeGreaterThanMaxDays(Date startDate, Date endDate, int maxDays) {
        final long days = Duration.between(startDate.toInstant(), endDate.toInstant()).toDays();
        return days > (maxDays - 1);
    }

    public static boolean isBookingOneDayAfter(Date startDate, Date today) {
        return startDate.after(today);
    }

    public static String mergeAllValidationDates(Date startDate, Date endDate, int maxDaysStay, int maxDaysPreviousBooking) {
        Date today = new Date();
        //Check the start date must be before than the leave date
        if (!DateUtils.isStartBeforeEnd(startDate, endDate)) {
            return "The start date can not be after than the end date";
        }
        //Only 3 days stay
        if (DateUtils.isTheRangeGreaterThanMaxDays(startDate, endDate, maxDaysStay)) {
            return "The stay must be for 3 days maximum";
        }
        //The reservation must not be done more than 30 days in advance
        if (DateUtils.isTheRangeGreaterThanMaxDays(today, startDate, maxDaysPreviousBooking)) {
            return "The reservation can not be done more than 30 days earlier";
        }
        //The arrival date must be at least 1 day after the booking date
        if (!DateUtils.isBookingOneDayAfter(startDate, today)) {
            return "The book must be start at least one day after the booking date";
        }
        return null;
    }

}
