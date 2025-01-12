package ru.practicum.shareit.booking.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exception.exceptions.BadRequestException;
import ru.practicum.shareit.exception.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Transactional // отказываем изменения
@SpringBootTest // создаём контекст
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingServiceImplTest {

    private final BookingService bookingService;
    private final UserService userService;

    @Test
    @DisplayName("Провряем коректное получение Booking из таблицы по ID")
    void getBookingByCorrectId() throws Exception {

        long userId = 1L;
        long bookingId = 1L;

        BookingDto bookingDto = bookingService.getBookingById(userId, bookingId);
        Assertions.assertEquals("WAITING", bookingDto.getStatus().name());
    }

    @Test
    @DisplayName("Провряем получение ошибки на запрос несуществующего booking")
    void getBookingByInCorrectId() throws Exception {

        long userId = 1L;
        long bookingId = 2L;

        Assertions.assertThrows(NotFoundException.class,
                () -> bookingService.getBookingById(userId, bookingId));

    }

    @Test
    @DisplayName("Проверяем получение всех bookings со State = ALL")
    void getAllUserBookingsWithAllState() {
        long userId = 1L;
        BookingState state = BookingState.ALL;

        List<BookingDto> dtos = bookingService.getAllUserBookings(userId, state);

        Assertions.assertEquals(1, dtos.size());
    }

    @Test
    @DisplayName("Проверяем получение всех bookings со State = FUTURE." +
            "В таблице bookings хранится booking с брониванием в прошлом.")
    void getAllUserBookingsWithFutureState() {
        long userId = 1L;
        BookingState state = BookingState.FUTURE;

        List<BookingDto> dtos = bookingService.getAllUserBookings(userId, state);

        Assertions.assertEquals(0, dtos.size());
    }


    @Test
    @DisplayName("Проверяем получаем всех вещей, на которое есть бронирование")
    void getAllUserBookingsItems() {
        long userId = 1L;
        BookingState state = BookingState.ALL;

        List<BookingDto> dtos = bookingService.getAllUserBookings(userId, state);
        BookingDto bookingDto = dtos.get(0);

        Assertions.assertEquals(1, bookingDto.getId());
        Assertions.assertEquals("Phone", bookingDto.getItem().getName());
    }

    @Test
    @DisplayName("Проверяем создание бронирование на вещь другим пользователем на попытку брони пользователем своей же вещи")
    void addNewBookingByOtherUser() {
        UserCreateDto createUserDto = UserCreateDto.builder().name("Vasya").email("vasya@yandex.ru").build();
        UserDto user = userService.createUser(createUserDto);
        long otherUserId = user.getId();

        BookingCreateDto createBookingDto = BookingCreateDto
                .builder()
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusHours(2))
                .itemId(1L)
                .build();

        BookingDto bookingDto = bookingService.addNewBooking(otherUserId, createBookingDto);

        Assertions.assertEquals(2L, bookingDto.getId());

    }

    @Test
    @DisplayName("Проверяем выброс исключение на попытку брони пользователем своей же вещи")
    void addNewBookingBySameUser() {
        long userId = 1L;
        BookingCreateDto createDto = BookingCreateDto
                .builder()
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusHours(2))
                .itemId(1L)
                .build();

        Assertions.assertThrows(NotFoundException.class,
                () -> bookingService.addNewBooking(userId, createDto));

    }

    @Test
    @DisplayName("Проверяем измнение статуса booking на APPROVED " +
            "и проверяем выбрасывание ошибки при повторной попытке изменения брони")
    void approveBooking() {
//        addNewUserAndBooking();

        long userId = 1L;
        long bookingId = 1L;
        Boolean approve = true;

        BookingDto bookingDto = bookingService.approveBooking(userId, bookingId, approve);
        Assertions.assertEquals(BookingStatus.APPROVED, bookingDto.getStatus());

        // попытка повторного изменения статуса должна привести к ошибке
        Assertions.assertThrows(BadRequestException.class,
                () -> bookingService.approveBooking(userId, bookingId, approve));

    }

    @Test
    @DisplayName("Проверяем измнение статус booking на REJECTED")
    void rejectedBooking() {

        long userId = 1L;
        long bookingId = 1L;
        Boolean approve = false;

        BookingDto bookingDto = bookingService.approveBooking(userId, bookingId, approve);
        Assertions.assertEquals(BookingStatus.REJECTED, bookingDto.getStatus());

    }


}