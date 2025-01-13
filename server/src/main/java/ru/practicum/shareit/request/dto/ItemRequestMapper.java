package ru.practicum.shareit.request.dto;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.itemDtos.ItemMapper;
import ru.practicum.shareit.item.dto.itemDtos.ItemResponseDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ItemRequestMapper {

    public static ItemRequest toItemRequestModel(ItemRequestCreateDto createDto, User user) {

        return ItemRequest.builder()
                .description(createDto.getDescription())
                .created(createDto.getCreated() != null ? createDto.getCreated() : LocalDateTime.now())
                .requestor(user)
                .build();
    }

    public static ItemRequestResponseDto toItemRequestDto(ItemRequest itemRequest) {

        List<ItemResponseDto> itemShortDtos;

        if (itemRequest.getItems() != null) {
            itemShortDtos = itemRequest.getItems().stream()
                    .map(item -> ItemMapper.toShortItemDto(item))
                    .toList();
        } else {
            itemShortDtos = null;
        }

        return ItemRequestResponseDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .items(itemShortDtos)
                .build();
    }

    public static ItemRequestResponseDto toItemRequestDto(ItemRequest itemRequest, List<ItemResponseDto> responseDtos) {

        return ItemRequestResponseDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .items(responseDtos)
                .build();
    }

}
