package ru.practicum.shareit.item.dto.itemDtos;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

@Component
public class ItemMapper {

    public Item toItemModel(ItemDto itemDto, User owner) {
        return Item.builder()
                .id(itemDto.getId())
                .owner(owner)
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .build();
    }

    public Item toItemModel(ItemCreateDto itemCreateDto, User owner) {
        return Item.builder()
                .owner(owner)
                .name(itemCreateDto.getName())
                .description(itemCreateDto.getDescription())
                .available(itemCreateDto.getAvailable())
                .build();
    }

    public Item requestToItemModel(ItemRequestDto itemRequestDto, User owner, ItemRequest request) {
        return Item.builder()
                .owner(owner)
                .name(itemRequestDto.getName())
                .description(itemRequestDto.getDescription())
                .available(itemRequestDto.getAvailable())
                .request(request)
                .build();
    }

//    public Item toItemModel(ItemUpdateDto itemUpdateDto) {
//    }

    public ItemDto toItemDto(Item item, User owner) {
        return ItemDto.builder()
                .id(item.getId())
                .owner(owner)
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .request(item.getRequest().getId())
                .build();
    }

    public ItemShortDto toShortDto(Item item) {
        return ItemShortDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .build();
    }

}
