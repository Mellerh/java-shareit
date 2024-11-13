package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemCreateDto {

    private Long ownerId;

    @NotBlank
    private String name;
    @NotBlank
    private String description;

    private Boolean availableStatus;
    private String request;

}
