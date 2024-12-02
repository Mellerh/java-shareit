package ru.practicum.shareit.booking.dto;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@Component
public class BookingMapper {

    public Booking toBookingModel(BookingCreateDto bookingCreateDto,
                                  User user, Item item) {
        return Booking.builder()
                .start(bookingCreateDto.getStartTime())
                .end(bookingCreateDto.getEndTime())
                .status(bookingCreateDto.getStatus() != null ? bookingCreateDto.getStatus() : BookingStatus.WAITING)
                .bookingItem(item)
                .booker(user)
                .build();
    }

    
}
