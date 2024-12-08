package ru.practicum.shareit.item.dto.commentDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CommentCreateDto {

    @NotBlank(message = "text должен быть заполнен")
    private String text;
    @NotNull(message = "itemId не может отсутствовать")
    private Long itemId;

}
