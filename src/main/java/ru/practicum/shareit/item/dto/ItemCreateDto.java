package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

@Data
@Builder
@AllArgsConstructor
public class ItemCreateDto {

    private User owner;

    @NotBlank
    private String name;
    @NotBlank
    private String description;

    private Boolean availableStatus;
    private String request;

}
