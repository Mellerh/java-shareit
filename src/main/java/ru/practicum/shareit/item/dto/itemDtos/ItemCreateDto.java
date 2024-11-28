package ru.practicum.shareit.item.dto.itemDtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemCreateDto {

    private Long userId;

    @NotBlank(message = "name должно быть заполнено")
    private String name;

    private String description;

    //    @NotNull(message = "available должно быть заполнено")
    private Boolean available;

    private Long requestId;

}
