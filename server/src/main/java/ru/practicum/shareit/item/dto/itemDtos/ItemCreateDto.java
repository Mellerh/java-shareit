package ru.practicum.shareit.item.dto.itemDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemCreateDto {

    private Long userId;

    private String name;

    private String description;

    private Boolean available;

    private Long requestId;

}
