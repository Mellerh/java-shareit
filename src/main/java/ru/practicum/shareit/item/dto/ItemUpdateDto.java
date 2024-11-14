package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

@Data
@Builder
@AllArgsConstructor
public class ItemUpdateDto {

    private User owner;

    private Long id;

    private String name;
    private String description;
    private Boolean available;

}
