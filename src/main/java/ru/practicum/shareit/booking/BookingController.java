package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exception.exceptions.DataConflictException;

import java.util.Collection;

@RestController
@RequestMapping(path = "/bookings")
@Validated
@RequiredArgsConstructor
public class BookingController {


    // X-Sharer-User-Id - это заголовок, из которого мы получаем userId
    private final String userIdFromHeader = "X-Sharer-User-Id";
    private final BookingService bookingService;

    /* Получение списка всех бронирований текущего пользователя */
    @GetMapping
    public Collection<BookingDto> getAllUserBookings(@RequestHeader(userIdFromHeader) Long userid,
                                                     @RequestParam(defaultValue = "ALL") BookingState state) {
        return bookingService.getAllUserBookings(userid, state);
    }

    /* Получение списка бронирований для всех вещей текущего пользователя */
    @GetMapping("/owner")
    public Collection<BookingDto> getAllUserBookingsItems(@RequestHeader(userIdFromHeader) Long userId,
                                                          @RequestParam(defaultValue = "ALL") BookingState state) {
        return bookingService.getAllUserBookingsItems(userId, state);
    }


    @PostMapping
    public BookingDto addNewBooking(@RequestHeader(userIdFromHeader) Long userId,
                                    @Valid @RequestBody BookingCreateDto bookingCreateDto) {
        return bookingService.addNewBooking(userId, bookingCreateDto);
    }


    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@RequestHeader(userIdFromHeader) Long userId,
                                     @PathVariable Long bookingId) throws DataConflictException {
        return bookingService.getBookingById(userId, bookingId);
    }

    /* одобрение бронирования */
    @PatchMapping("/{bookingId}")
    public BookingDto approveBooking(@RequestHeader(userIdFromHeader) Long userId,
                                     @PathVariable Long bookingId,
                                     @RequestParam Boolean approved) {
        return bookingService.approveBooking(userId, bookingId, approved);
    }



}
