package ru.practicum.shareit.item.dto;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

@Component
public class ItemMapper {

    public Item toItemModel(ItemDto itemDto) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .availableStatus(itemDto.getAvailableStatus())
                .request(itemDto.getRequest())
                .build();
    }

    public Item toItemModel(ItemCreateDto itemCreateDto, Long ownerId) {
        return Item.builder()
                .ownerId(ownerId)
                .name(itemCreateDto.getName())
                .description(itemCreateDto.getDescription())
                .availableStatus(itemCreateDto.getAvailableStatus())
                .request(itemCreateDto.getRequest())
                .build();
    }

//    public Item toItemModel(ItemUpdateDto itemUpdateDto) {
//        private Long ownerId;
//
//        @NotNull
//        @Positive
//        private Long id;
//
//        private String name;
//        private String description;
//        private Boolean availableStatus;
//        private String request;
//    }

    public ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .ownerId(item.getOwnerId())
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .availableStatus(item.getAvailableStatus())
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
