package com.github.carloscontrerasruiz.booking.service.interfaces;

import com.github.carloscontrerasruiz.booking.dto.GeneralResponse;
import com.github.carloscontrerasruiz.booking.dto.cancellation.BookCancellationResp;

import java.util.List;

public interface BookingCancellationService {
    GeneralResponse<List<BookCancellationResp>> getAllCancellations();

    GeneralResponse<BookCancellationResp> getCancellationByCancelId(int id);

    GeneralResponse<BookCancellationResp> getCancellationByBookId(int id);
}
