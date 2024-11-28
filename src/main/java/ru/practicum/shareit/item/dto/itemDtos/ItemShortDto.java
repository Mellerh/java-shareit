package ru.practicum.shareit.item.dto.itemDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
/* возвращаем короткое описание Item */
public class ItemShortDto {

    private String name;
    private String description;
    private Boolean available;

}
