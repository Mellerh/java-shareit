package ru.practicum.shareit.booking.dto;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.itemDtos.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;

@Component
public class BookingMapper {

    public static Booking toBookingModel(BookingCreateDto bookingCreateDto,
                                  User user, Item item) {
        return Booking.builder()
                .start(bookingCreateDto.getStartTime())
                .end(bookingCreateDto.getEndTime())
                .status(bookingCreateDto.getStatus() != null ? bookingCreateDto.getStatus() : BookingStatus.WAITING)
                .bookingItem(item)
                .booker(user)
                .build();
    }

    public static BookingDto toBookingDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .status(booking.getStatus())
                .bookingItem(ItemMapper.toShortItemDto(booking.getBookingItem()))
                .booker(UserMapper.toUserDto(booking.getBooker()))
                .build();
    }


}
