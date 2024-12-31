package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.client.BookingClient;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.exception.exceptions.DataConflictException;


@RestController
@RequestMapping(path = "/bookings")
@Validated
@RequiredArgsConstructor
public class BookingController {


    // X-Sharer-User-Id - это заголовок, из которого мы получаем userId
    private final String userIdFromHeader = "X-Sharer-User-Id";
    private final BookingClient bookingService;

    /* Получение списка всех бронирований текущего пользователя */
    @GetMapping
    public ResponseEntity<Object> getAllUserBookings(@RequestHeader(userIdFromHeader) Long userid,
                                                     @RequestParam(defaultValue = "ALL") BookingState state) {
        return bookingService.getAllUserBookings(userid, state);
    }

    /* Получение списка бронирований для всех вещей текущего пользователя */
    @GetMapping("/owner")
    public ResponseEntity<Object> getAllUserBookingsItems(@RequestHeader(userIdFromHeader) Long userId,
                                                          @RequestParam(defaultValue = "ALL") BookingState state) {
        return bookingService.getAllUserBookingsItems(userId, state);
    }


    @PostMapping
    public ResponseEntity<Object> addNewBooking(@RequestHeader(userIdFromHeader) Long userId,
                                                @Valid @RequestBody BookingCreateDto bookingCreateDto) {
        return bookingService.addNewBooking(userId, bookingCreateDto);
    }


    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBookingById(@RequestHeader(userIdFromHeader) Long userId,
                                                 @PathVariable Long bookingId) throws DataConflictException {
        return bookingService.getBookingById(userId, bookingId);
    }

    /* одобрение бронирования */
    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approveBooking(@RequestHeader(userIdFromHeader) Long userId,
                                                 @PathVariable Long bookingId,
                                                 @RequestParam Boolean approved) {
        return bookingService.approveBooking(userId, bookingId, approved);
    }


}
