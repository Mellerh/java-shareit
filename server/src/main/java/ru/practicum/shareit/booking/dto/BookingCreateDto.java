package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BookingCreateDto {

    private LocalDateTime start;
    private LocalDateTime end;

    private Long itemId;

    private BookingStatus status;

}
