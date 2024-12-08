package ru.practicum.shareit.item.dto.itemDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.item.dto.commentDtos.CommentDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ItemResponseDto {

    private Long id;

    private String name;
    private String description;
    private Boolean available;
    private BookingShortDto lastBooking;
    private BookingShortDto nextBooking;
    private List<CommentDto> comments;
}
