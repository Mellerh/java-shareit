package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.dto.markerInterfaces.Update;
import ru.practicum.shareit.user.model.User;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
@AllArgsConstructor
public class ItemDto {

    private User owner;

    @NotNull(groups = {Update.class})
    @Positive(groups = {Update.class})
    private Long id;

    private String name;
    private String description;
    private Boolean availableStatus;
    private String request;

}
