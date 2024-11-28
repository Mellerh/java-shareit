package ru.practicum.shareit.item.dto;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@Component
public class ItemMapper {

    public Item toItemModel(ItemDto itemDto, User owner) {
        return Item.builder()
                .id(itemDto.getId())
                .owner(owner)
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .is_available(itemDto.getIs_available())
                .request(itemDto.getRequest())
                .build();
    }

    public Item toItemModel(ItemCreateDto itemCreateDto, User owner) {
        return Item.builder()
                .owner(owner)
                .name(itemCreateDto.getName())
                .description(itemCreateDto.getDescription())
                .is_available(itemCreateDto.getIs_available())
                .request(itemCreateDto.getRequest())
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
                .is_available(item.getIs_available())
                .request(item.getRequest())
                .build();
    }

    public ItemShortDto toShortDto(Item item) {
        return ItemShortDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .build();
    }

}
