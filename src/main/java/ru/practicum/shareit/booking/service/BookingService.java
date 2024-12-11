package ru.practicum.shareit.booking.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.exception.exceptions.DataConflictException;

import java.util.List;

@Service
public interface BookingService {

    BookingDto addNewBooking(Long userId, BookingCreateDto bookingCreateDto);

    BookingDto approveBooking(Long userId, Long bookingId, Boolean approved);

    BookingDto getBookingById(Long userId, Long bookingId) throws DataConflictException;

    List<BookingDto> getAllUserBookings(Long userId, BookingState state);

    List<BookingDto> getAllUserBookingsItems(Long userId, BookingState state);

}
