package ru.practicum.shareit.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.BookingController;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.itemDtos.ItemResponseDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc
public class BookingControllerTest {

    @MockBean
    private BookingService bookingService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;



    @Test
    @DisplayName("Тестриуем работу get-запроса на /bookings и получение пустого списка")
    void getAllUserBookingsEmptyList() throws Exception {

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
    @DisplayName("Тестриуем работу get-запроса на /bookings и получение списка с одним бронированием")
    void getAllUserBookingsWithOneBooking() throws Exception {

        String state = "WAITING";
        long userId = 1L;

        List<BookingDto> bookingDtos = List.of(createResponseDto());

        Mockito
                .when(bookingService.getAllUserBookings((userId), BookingState.WAITING))
                .thenReturn(bookingDtos);

        mockMvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", userId)
                        .param("state", state))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(bookingDtos.get(0).getId()), Long.class)
                );

        Mockito.verify(bookingService, Mockito.times(1))
                .getAllUserBookings((userId), BookingState.WAITING);
    }


    @Test
    @DisplayName("Тестриуем работу get-запроса на /bookings/owner и получение списка с одним бронированием")
    void getAllUserBookingsItems() throws Exception {

        String state = "WAITING";
        long userId = 1L;

        List<BookingDto> bookingDtos = List.of(createResponseDto());

        Mockito
                .when(bookingService.getAllUserBookingsItems((userId), BookingState.WAITING))
                .thenReturn(bookingDtos);

        mockMvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", userId)
                        .param("state", state))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(bookingDtos.get(0).getId()), Long.class))
                .andExpect(jsonPath("$[0].item.id", is(bookingDtos.get(0).getItem().getId()), Long.class))
                .andExpect(jsonPath("$[0].booker.id", is(bookingDtos.get(0).getBooker().getId()), Long.class)


                );

        Mockito.verify(bookingService, Mockito.times(1))
                .getAllUserBookingsItems((userId), BookingState.WAITING);
    }

    @Test
    @DisplayName("Тестриуем работу post-запроса на /bookings и создание бронирования")
    void addNewBookingWithAvailableItem() throws Exception {

        BookingCreateDto createDto = createRequestDto();
        BookingDto bookingDto = createResponseDto();

        Mockito
                .when(bookingService.addNewBooking(Mockito.anyLong(), Mockito.any()))
                .thenReturn(bookingDto);

        mockMvc.perform(post("/bookings")
                .content(mapper.writeValueAsString(createDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .header("X-Sharer-User-Id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpectAll(
                status().isOk(),
                jsonPath("$.id", is(bookingDto.getId()), Long.class),
                jsonPath("$.start", notNullValue()),
                jsonPath("$.end", notNullValue()),
                jsonPath("$.status", equalTo(bookingDto.getStatus().name())),
                jsonPath("$.item.id", is(bookingDto.getItem().getId()), Long.class),
                jsonPath("$.booker.id", is(bookingDto.getBooker().getId()), Long.class)
        );

    }

    @Test
    @DisplayName("Тестриуем работу get-запроса на /bookings/{bookingId}")
    void getBookingByCorrectId() throws Exception {

        BookingDto bookingDto = createResponseDto();

        Mockito
                .when(bookingService.getBookingById(1L, 1L))
                .thenReturn(bookingDto);

        mockMvc.perform(get("/bookings/1")
                .header("X-Sharer-User-Id", 1))
        .andExpectAll(
                status().isOk(),
                jsonPath("$.id", is(bookingDto.getId()), Long.class),
                jsonPath("$.start", notNullValue()),
                jsonPath("$.end", notNullValue()),
                jsonPath("$.status", equalTo(bookingDto.getStatus().name())),
                jsonPath("$.item.id", is(bookingDto.getItem().getId()), Long.class),
                jsonPath("$.booker.id", is(bookingDto.getBooker().getId()), Long.class)
        );

    }

    @Test
    @DisplayName("Тестриуем запрос на некорректный Api и получения ошибки")
    void testIncorrectApi() throws Exception {

        BookingDto bookingDto = createResponseDto();

        Mockito
                .when(bookingService.getBookingById(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(bookingDto);

        mockMvc.perform(get("/bookings/null")
                    .header("X-Sharer-User-Id", 1))
                .andExpect(status().isInternalServerError());

        Mockito.verify(bookingService, Mockito.never())
                .getBookingById(Mockito.anyLong(), Mockito.anyLong());

    }

    @Test
    @DisplayName("Тестриуем patch-запрос на /bookings/{bookingId}")
    void approveBooking() throws Exception {

        BookingDto bookingDto = createResponseDto();

        Mockito
                .when(bookingService.approveBooking(Mockito.anyLong(),
                                                            Mockito.anyLong(),
                                                            Mockito.anyBoolean()))
                .thenReturn(bookingDto);

        mockMvc.perform(patch("/bookings/1")
                .header("X-Sharer-User-Id", 1)
                .param("approved", "true"))
        .andExpectAll(
                jsonPath("$.id", is(bookingDto.getId()), Long.class)
                );

    }


    private BookingCreateDto createRequestDto() {
        return BookingCreateDto.builder()
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusHours(2))
                .status(BookingStatus.WAITING)
                .itemId(1L)
                .build();
    }

    private BookingDto createResponseDto() {
        ItemResponseDto item = ItemResponseDto.builder()
                .id(1L)
                .name("Some")
                .description("Some desc")
                .available(true)
                .build();

        UserDto booker = UserDto.builder()
                .id(1L)
                .name("Name")
                .build();

        return BookingDto.builder()
                .id(1L)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusHours(2))
                .status(BookingStatus.WAITING)
                .item(item)
                .booker(booker)
                .build();
    }

}

