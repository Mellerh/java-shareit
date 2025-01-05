package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc
class BookingControllerTest {

    @MockBean
    private BookingService bookingService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;



    @Test
    void getAllUserBookings() throws Exception {

        String state = "PAST";
        long userId = 1L;

        Mockito
                .when(bookingService.getAllUserBookings((userId), BookingState.PAST))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/bookings")
                                    .header("X-Sharer-User-Id", userId)
                                    .param("state", state))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        Mockito.verify(bookingService, Mockito.times(1))
                .getAllUserBookings((userId), BookingState.PAST);
    }


    @Test
    void getAllUserBookingsItems() {
    }

    @Test
    void addNewBooking() {
    }

    @Test
    void getBookingById() {
    }

    @Test
    void approveBooking() {
    }
}

