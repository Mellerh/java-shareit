package ru.practicum.shareit.booking.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(of = "id")
public class Booking {

    private Long id;
    private LocalDate start;
    private LocalDate end;
    private Item bookingItem;
    private User booker;
    private BookingStatus status;

    enum BookingStatus {
        WAITING,
        APPROVED,
        REJECTED,
        CANCELED
    }

}
