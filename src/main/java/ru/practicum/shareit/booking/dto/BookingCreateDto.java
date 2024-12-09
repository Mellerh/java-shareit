package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BookingCreateDto {

    @FutureOrPresent
    private LocalDateTime start;
    @Future
    private LocalDateTime end;

    private Long itemId;

    private BookingStatus status;

    @AssertTrue(message = "Некооректные даты бронирования. Начало аренды не может идти после её окончания.")
    public boolean isStartBeforeEnd() {
        return this.start.isBefore(this.end);
    }

}
