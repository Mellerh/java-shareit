package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemUpdateDto {

    private Long ownerId;

    @NotNull
    @Positive
    private Long id;

    private String name;
    private String description;
    private Boolean availableStatus;
    private String request;

}
