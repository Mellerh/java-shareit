package ru.practicum.shareit.item.dto.itemDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingDto;

@Data
@Builder
@AllArgsConstructor
public class ItemWithBookingDto {

    private Long id;

    private String name;
    private String description;
    private Boolean available;
    private BookingDto lastBooking;
    private BookingDto nextBooking;
//    private List<CommentDto> comments;
}
