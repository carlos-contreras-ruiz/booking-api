package com.github.carloscontrerasruiz.booking.controller;

import com.github.carloscontrerasruiz.booking.dto.GeneralResponse;
import com.github.carloscontrerasruiz.booking.dto.cancellation.BookCancellationResp;
import com.github.carloscontrerasruiz.booking.service.interfaces.BookingCancellationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/booking/cancellation")
public class BookingCancellationController {

    @Autowired
    private BookingCancellationService service;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<GeneralResponse<List<BookCancellationResp>>> getAllCancellations() {
        GeneralResponse<List<BookCancellationResp>> response = service.getAllCancellations();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<GeneralResponse<BookCancellationResp>> getCancellationById(@PathVariable int id) {
        GeneralResponse<BookCancellationResp> response = service.getCancellationByCancelId(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping(value = "/book/{id}", produces = "application/json")
    public ResponseEntity<GeneralResponse<BookCancellationResp>> getCancellationByBookId(@PathVariable int id) {
        GeneralResponse<BookCancellationResp> response = service.getCancellationByBookId(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
