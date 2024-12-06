package ru.practicum.shareit.item.dto.itemDtos;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@Component
public class ItemMapper {

    public static Item toItemModel(ItemDto itemDto, User owner) {
        return Item.builder()
                .id(itemDto.getId())
                .owner(owner)
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .build();
    }

    public static Item toItemModel(ItemCreateDto itemCreateDto, User owner) {
        return Item.builder()
                .owner(owner)
                .name(itemCreateDto.getName())
                .description(itemCreateDto.getDescription())
                .available(itemCreateDto.getAvailable())
                .build();
    }


//    public Item toItemModel(ItemUpdateDto itemUpdateDto) {
//    }

    public static ItemDto toItemDto(Item item, User owner) {
        return ItemDto.builder()
                .id(item.getId())
                .owner(owner)
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .request(item.getRequest().getId())
                .build();
    }

    public static ItemResponseDto toShortItemDto(Item item) {
        return ItemResponseDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    public static ItemResponseDto toItemDtoWithBooking(Item item, BookingShortDto lastBooking, BookingShortDto nextBooking) {
        ItemResponseDto itemResponseDto = toShortItemDto(item);
        itemResponseDto.setLastBooking(lastBooking);
        itemResponseDto.setNextBooking(nextBooking);
        return itemResponseDto;
    }

}
