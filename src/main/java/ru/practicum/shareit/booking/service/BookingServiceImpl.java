package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.exceptions.AccessDeniedException;
import ru.practicum.shareit.exception.exceptions.DataConflictException;
import ru.practicum.shareit.exception.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;


    @Override
    public BookingDto getBookingById(Long userId, Long bookingId) throws DataConflictException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с id " + userId + " не найден."));

        Booking bookingReq = bookingRepository.findById(bookingId).orElseThrow(()
                -> new NotFoundException("Booking с id " + bookingId + " не найден."));

        if (user.getId().equals(bookingReq.getBooker().getId()) ||
                userId.equals(bookingReq.getBookingItem().getOwner().getId())) {
            return BookingMapper.toBookingDto(bookingReq);
        } else {
            throw new DataConflictException("Пользователь с id " + userId
                    + " не является ни автором бронирования ни владельцем вещи");
        }

    }


    @Override
    public List<BookingDto> getAllUserBookings(Long userId, BookingState state) {

    }


    @Override
    public List<BookingDto> getAllUserBookingsItems(Long userId, BookingState state) {


    }


    @Override
    public BookingDto addNewBooking(Long userId, BookingCreateDto bookingCreateDto) {
        Item item = itemRepository.findById(bookingCreateDto.getItemBookingId()).orElseThrow(()
                -> new NotFoundException("Item с id " + bookingCreateDto.getItemBookingId() + " не найден."));

        if (!item.getAvailable()) {
            throw new AccessDeniedException("Item с id " + item.getId() + " не доступен для брони.");
        }

        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Пользователь с id " + userId + " не найден."));

        Booking newBooking = BookingMapper.toBookingModel(bookingCreateDto, user, item);

        return BookingMapper.toBookingDto(bookingRepository.save(newBooking));
    }


    @Override
    public BookingDto approveBooking(Long userId, Long bookingId, Boolean approved) throws DataConflictException {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Пользователь с id " + userId + " не найден."));

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()
                -> new NotFoundException("Booking с id " + bookingId + " не найден."));

        // если userId не является владельцем вещи, на которое создан запрос одобрения
        if (!user.getId().equals(booking.getBookingItem().getId())) {
            throw new DataConflictException("Пользователь с id " + userId
                    + " не является владельцем вещи");
        }

        if (booking.getStatus() != null && (booking.getStatus() == BookingStatus.APPROVED
                || booking.getStatus() == BookingStatus.REJECTED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Невозможно изменить статус аренды после.");
        }

        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }

        return BookingMapper.toBookingDto(bookingRepository.save(booking));

    }
}
