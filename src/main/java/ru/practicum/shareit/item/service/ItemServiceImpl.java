package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.exceptions.BadRequestException;
import ru.practicum.shareit.exception.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.commentDtos.CommentCreateDto;
import ru.practicum.shareit.item.dto.commentDtos.CommentDto;
import ru.practicum.shareit.item.dto.commentDtos.CommentMapper;
import ru.practicum.shareit.item.dto.itemDtos.*;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {


    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;


    @Override
    public ItemResponseDto getItemById(Long userId, Long itemId) {
        LocalDateTime now = LocalDateTime.now();

        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("User с id " + userId + " не найден."));

        Item item = itemRepository.findById(itemId).orElseThrow(()
                -> new NotFoundException("Item с id " + itemId + " не найден."));

        List<CommentDto> commentList = commentRepository.findAllByItemIdOrderByCreatedDesc(itemId)
                .stream().map(comment -> CommentMapper.toCommentDto(comment)).toList();

        BookingShortDto nextBooking = BookingMapper.toBookingShortDto(
                bookingRepository.findNextBookingByItemId(itemId, now));
        BookingShortDto lastBooking = BookingMapper.toBookingShortDto(
                bookingRepository.findLastBookingByItemId(itemId, now));


        if (user.equals(item.getOwner())) {
            return ItemMapper.toItemDtoWithBooking(item, lastBooking, nextBooking, commentList);
        } else {
            return ItemMapper.toItemDtoWithBooking(item, null, null, commentList);
        }

    }


    @Override
    public Collection<ItemResponseDto> getAllUserItems(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("User с id " + userId + " не найден."));

        List<Item> itemList = itemRepository.findAllByOwnerId(user.getId());
        List<Long> itemIds = itemList.stream().map(item -> item.getId()).toList();
        List<Booking> bookingListByItemIdIn = bookingRepository.findByItem_IdIn(itemIds);
        List<Comment> commentList = commentRepository.findAllByItemIdIn(itemIds);

        if (!bookingListByItemIdIn.isEmpty()) {
            return upgradeItemsInfo(itemList, bookingListByItemIdIn, commentList, now);
        } else {
            return itemList.stream().map(item -> ItemMapper.toShortItemDto(item)).toList();
        }
    }


    @Override
    public ItemResponseDto addNewItem(Long userId, ItemCreateDto itemDto) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("User с id " + userId + " не найден."));

        Item item = ItemMapper.toItemModel(itemDto, user);

        return ItemMapper.toShortItemDto(itemRepository.save(item));
    }


    @Override
    public ItemResponseDto updateItem(Long userId, Long itemId, ItemUpdateDto itemUpdate) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("User с id " + userId + " не найден."));

        // возможно, тут надо получать id из itemUpdateDto
        Item item = itemRepository.findById(itemId).orElseThrow(()
                -> new NotFoundException("Item с id " + itemId + " не найдена."));


        if (!Objects.equals(user.getId(), item.getOwner().getId())) {
            throw new NotFoundException("Item с id " + itemId + " не принадлежит User с id " + userId);
        }

        if (itemUpdate.getName() != null) {
            item.setName(itemUpdate.getName());
        }

        if (itemUpdate.getDescription() != null) {
            item.setDescription(itemUpdate.getDescription());
        }

        if (itemUpdate.getAvailable() != null) {
            item.setAvailable(itemUpdate.getAvailable());
        }

        item.setOwner(user);

        return ItemMapper.toShortItemDto(itemRepository.save(item));

    }


    @Override
    public Collection<ItemDto> getAvailableItemsByText(String text) {
        if (text.isEmpty()) {
            return new ArrayList<>();
        }

        return itemRepository.searchByText(text).stream()
                .map(item -> ItemMapper.toItemDto(item, item.getOwner()))
                .toList();
    }

    @Override
    public CommentDto createCommentForItem(Long userId, Long itemId, CommentCreateDto createDto) {

        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("User с id " + userId + " не найден."));

        Item item = itemRepository.findById(itemId).orElseThrow(()
                -> new NotFoundException("Item с id " + itemId + " не найдена."));

        LocalDateTime now = LocalDateTime.now();
        Booking booking = bookingRepository.findFirstByBookerIdAndItemIdAndEndBefore(userId, itemId, now);
        if (booking == null) {
            throw new BadRequestException("Пользователь " + user + " не брал в аренду вещь " + itemId);
        }

        Comment comment = CommentMapper.toCommentModel(createDto, item, user);

        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }








    /* метод добавляет в Item связанные с ним Booking */
    private Collection<ItemResponseDto> upgradeItemsInfo(List<Item> itemList, List<Booking> bookingList,
                                                         List<Comment> commentList, LocalDateTime now) {

        List<ItemResponseDto> dtoList = new ArrayList<>();


        // метод группирует список бронирований (bookingList) по id вещей, связанных с каждым бронированием.
        // В результате получается Map, где ключом является id вещи (item),
        // а значением — список бронирований, относящихся к этой вещи.
        Map<Long, List<Booking>> bookingListMap = bookingList.stream()
                .collect(Collectors.groupingBy(booking -> booking.getItem().getId()));

        Map<Long, List<Comment>> commentListMap = commentList.stream()
                .collect(Collectors.groupingBy(comment -> comment.getItem().getId()));

        // на стороне кода сортируем booking по дате и находим нужные
        for (Item item : itemList) {
            List<CommentDto> commentDtos = new ArrayList<>();

            BookingShortDto lastBooking = bookingListMap.getOrDefault(item.getId(), Collections.emptyList()).stream()
                    .filter(booking -> booking.getStart().isAfter(now))
                    .sorted(Comparator.comparing(booking -> booking.getStart(), Comparator.naturalOrder()))
                    .map(booking -> BookingMapper.toBookingShortDto(booking))
                    .findFirst().orElse(null);

            BookingShortDto nextBooking = bookingListMap.getOrDefault(item.getId(), Collections.emptyList()).stream()
                    .filter(booking -> booking.getStart().isBefore(now))
                    .sorted(Comparator.comparing(booking -> booking.getStart(), Comparator.reverseOrder()))
                    .map(booking -> BookingMapper.toBookingShortDto(booking))
                    .findFirst().orElse(null);

            List<Comment> comments = commentListMap.getOrDefault(item.getId(), Collections.emptyList());
            if (comments != null) {
                commentDtos = comments.stream().map(comment -> CommentMapper.toCommentDto(comment)).toList();
            }

            dtoList.add(ItemMapper.toItemDtoWithBooking(item, lastBooking, nextBooking, commentDtos));

        }

        return dtoList;

    }

}
