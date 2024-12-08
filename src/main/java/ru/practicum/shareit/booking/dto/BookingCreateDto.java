package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
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

    @FutureOrPresent
    private LocalDateTime startTime;
    @Future
    private LocalDateTime endTime;

    @NotNull
    private Long itemBookingId;

    private BookingStatus status;

    @AssertTrue(message = "Некооректные даты бронирования. Начало аренды не может идти после её окончания.")
    boolean isStartBeforeEnd() {
        return startTime.isBefore(endTime);
    }

}
