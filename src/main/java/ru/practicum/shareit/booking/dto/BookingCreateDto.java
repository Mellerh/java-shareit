package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class BookingCreateDto {

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @NotNull
    private Long itemBookingId;

    private BookingStatus status;

}
