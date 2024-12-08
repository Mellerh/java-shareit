package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {


    List<Booking> findByBooker_Id(Long bookerId, Sort sort);

    List<Booking> findByBooker_IdAndStartBeforeAndEndAfter(Long bookerId, LocalDateTime start, LocalDateTime end, Sort sort);

    List<Booking> findByBooker_IdAndEndBefore(Long bookerId, LocalDateTime time, Sort sort);

    List<Booking> findByBooker_IdAndStartAfter(Long bookerId, LocalDateTime time, Sort sort);

    List<Booking> findByBooker_IdAndStatus(Long bookerId, BookingStatus status, Sort sort);


    List<Booking> findByItem_IdIn(List<Long> itemIds);

    List<Booking> findByItem_IdIn(List<Long> itemIds, Sort sort);

    List<Booking> findByItem_IdInAndStartBeforeAndEndAfter(List<Long> itemIds, LocalDateTime start, LocalDateTime end, Sort sort);

    List<Booking> findByItem_IdInAndEndBefore(List<Long> itemIds, LocalDateTime end, Sort sort);

    List<Booking> findByItem_IdInAndStartAfter(List<Long> itemIds, LocalDateTime start, Sort sort);

    List<Booking> findByItem_IdInAndStatus(List<Long> itemIds, BookingStatus status, Sort sort);

    Booking findFirstByBookerIdAndItemIdAndEndBefore(Long userId, Long itemId, LocalDateTime now);

    @Query("select booking from Booking booking " +
            "join booking.item item " +
            "where item.id = ?1 " +
            "and booking.start > ?2 " +
            "and booking.status not in ('REJECTED', 'CANCELLED') " +
            "order by booking.start asc " +
            "limit 1")
    Booking findNextBookingByItemId(Long itemId, LocalDateTime now);

    @Query("select booking from Booking booking " +
            "join booking.item item " +
            "where item.id = ?1 " +
            "and booking.end <= ?2 " +
            "and booking.status not in ('REJECTED', 'CANCELLED') " +
            "order by booking.start desc " +
            "limit 1")
    Booking findLastBookingByItemId(Long itemId, LocalDateTime now);
}
