package com.github.carloscontrerasruiz.booking.controller;

import com.github.carloscontrerasruiz.booking.dto.CheckAvailabilityResDto;
import com.github.carloscontrerasruiz.booking.dto.GeneralResponse;
import com.github.carloscontrerasruiz.booking.service.interfaces.BookingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @MockBean
    private BookingService bookingService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void checkRoomAvailability() throws Exception {
        Mockito.when(bookingService.checkRoomAvailability(Mockito.any()))
                .thenReturn(GeneralResponse.<CheckAvailabilityResDto>builder()
                        .httpCode("200")
                        .status(HttpStatus.OK)
                        .body(CheckAvailabilityResDto.builder().roomAvailable(true).build())
                        .build()
                );

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/booking/checkRoomAvailability")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\n" +
                                        "    \"startDate\":\"06/15/2022\",\n" +
                                        "    \"endDate\":\"06/17/2022\"\n" +
                                        "}")
                )
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(
                                containsString("roomAvailable")
                        )
                );

        Mockito.verify(bookingService).checkRoomAvailability(Mockito.any());
    }
}