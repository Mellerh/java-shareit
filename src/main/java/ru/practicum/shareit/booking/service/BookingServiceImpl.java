package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.exceptions.BadRequestException;
import ru.practicum.shareit.exception.exceptions.DataConflictException;
import ru.practicum.shareit.exception.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
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
                userId.equals(bookingReq.getItem().getOwner().getId())) {
            return BookingMapper.toBookingDto(bookingReq);
        } else {
            throw new DataConflictException("Пользователь с id " + userId
                    + " не является ни автором бронирования ни владельцем вещи");
        }

    }


    @Override
    public List<BookingDto> getAllUserBookings(Long userId, BookingState state) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Пользователь с id " + userId + " не найден."));

        LocalDateTime currentTime = LocalDateTime.now();
        Sort sortByStart = Sort.by(Sort.Direction.DESC, "start");

        List<Booking> bookingList = switchStateBooking(userId, state, sortByStart, currentTime);

        return bookingList.stream()
                .map(booking -> BookingMapper.toBookingDto(booking))
                .toList();
    }


    @Override
    public List<BookingDto> getAllUserBookingsItems(Long userId, BookingState state) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Пользователь с id " + userId + " не найден."));

        List<Item> itemList = itemRepository.findAllByOwnerId(user.getId());
        if (itemList.isEmpty()) {
            return List.of();
        }

        // все id вещей, на которые есть бронирования
        List<Long> itemIds = itemList.stream().map(item -> item.getId()).toList();
        LocalDateTime currentTime = LocalDateTime.now();
        Sort sortByStart = Sort.by(Sort.Direction.DESC, "start");

        List<Booking> bookingList = switchStateBookingItems(itemIds, state, sortByStart, currentTime);

        return bookingList.stream()
                .map(booking -> BookingMapper.toBookingDto(booking))
                .toList();
    }


    @Override
    public BookingDto addNewBooking(Long userId, BookingCreateDto bookingCreateDto) {
        Item item = itemRepository.findById(bookingCreateDto.getItemId()).orElseThrow(()
                -> new NotFoundException("Item с id " + bookingCreateDto.getItemId() + " не найден."));


        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Пользователь с id " + userId + " не найден."));

        if (!item.getAvailable()) {
            throw new BadRequestException("Item с id " + item.getId() + " не доступен для брони.");
        }

        if (item.getOwner().equals(user)) {
            throw new NotFoundException("Бронь вещи " + item.getId() + " не доступна для пользователя " + user.getId());
        }

        Booking newBooking = BookingMapper.toBookingModel(bookingCreateDto, user, item);

        return BookingMapper.toBookingDto(bookingRepository.save(newBooking));
    }


    @Override
    public BookingDto approveBooking(Long userId, Long bookingId, Boolean approved) {
//        User user = userRepository.findById(userId).orElseThrow(()
//                -> new NotFoundException("Пользователь с id " + userId + " не найден."));
//
//        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()
//                -> new NotFoundException("Booking с id " + bookingId + " не найден."));

        Booking booking = bookingRepository.findBookingByOwner(userId, bookingId);
        if (booking == null) {
            throw new BadRequestException("Пользователь с id " + userId
                    + " не является владельцем вещи c id " + bookingId);
        }

        // если userId не является владельцем вещи, на которое создан запрос одобрения
//        if (!userId.equals(booking.getItem().getOwner().getId())) {
//            throw new BadRequestException("Пользователь с id " + userId
//                    + " не является владельцем вещи c id " + booking.getItem().getId());
//        }

        if (booking.getStatus() != null && (booking.getStatus() == BookingStatus.APPROVED
                || booking.getStatus() == BookingStatus.REJECTED)) {
            throw new BadRequestException("Статус бронирования уже изменён.");
        }

        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }

        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }


    /* метод вызывает соответсвующий метод репозитория в зависимости от state */
    private List<Booking> switchStateBooking(Long userId, BookingState state, Sort sortByStart, LocalDateTime currentTime) {

        return switch (state) {
            case ALL -> bookingRepository.findByBooker_Id(userId, sortByStart);
            case CURRENT -> bookingRepository.findByBooker_IdAndStartBeforeAndEndAfter(userId,
                    currentTime, currentTime, sortByStart);
            case PAST -> bookingRepository.findByBooker_IdAndEndBefore(userId, currentTime, sortByStart);
            case FUTURE -> bookingRepository.findByBooker_IdAndStartAfter(userId, currentTime, sortByStart);
            case WAITING -> bookingRepository.findByBooker_IdAndStatus(userId, BookingStatus.WAITING,
                    sortByStart);
            case REJECTED -> bookingRepository.findByBooker_IdAndStatus(userId, BookingStatus.REJECTED,
                    sortByStart);
            default -> throw new NotFoundException("Состояние " + state + " не найдено.");
        };

    }

    private List<Booking> switchStateBookingItems(List<Long> itemIds, BookingState state, Sort sortByStart,
                                                  LocalDateTime currentTime) {
        return switch (state) {
            case ALL -> bookingRepository.findByItem_IdIn(itemIds, sortByStart);
            case CURRENT -> bookingRepository.findByItem_IdInAndStartBeforeAndEndAfter(itemIds, currentTime,
                    currentTime, sortByStart);
            case PAST -> bookingRepository.findByItem_IdInAndEndBefore(itemIds, currentTime, sortByStart);
            case FUTURE -> bookingRepository.findByItem_IdInAndStartAfter(itemIds, currentTime, sortByStart);
            case WAITING -> bookingRepository.findByItem_IdInAndStatus(itemIds, BookingStatus.WAITING, sortByStart);
            case REJECTED -> bookingRepository.findByItem_IdInAndStatus(itemIds, BookingStatus.REJECTED, sortByStart);
            default -> throw new UnsupportedOperationException("Неизвестное состояние: " + state);
        };
    }

}
