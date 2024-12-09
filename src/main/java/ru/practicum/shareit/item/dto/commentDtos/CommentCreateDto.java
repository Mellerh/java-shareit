package ru.practicum.shareit.item.dto.commentDtos;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentCreateDto {

    //    @NotBlank(message = "text должен быть заполнен")
    private String text;

}
