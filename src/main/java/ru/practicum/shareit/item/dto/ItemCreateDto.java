package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

@Data
@Builder
@AllArgsConstructor
public class ItemCreateDto {

    private User owner;

    @NotBlank(message = "name должно быть заполнено")
    private String name;
    @NotBlank(message = "description дожно быть заполнено")
    private String description;

    @NotNull(message = "available должно быть заполнено")
    private Boolean available;
    private String request;

}
