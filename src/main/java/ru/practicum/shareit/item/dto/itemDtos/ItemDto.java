package ru.practicum.shareit.item.dto.itemDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.model.User;


@Builder
@Data
@AllArgsConstructor
public class ItemDto {

    private User owner;
    private Long id;

    private String name;
    private String description;
    private Boolean available;
    private Long request;

}
