package ru.practicum.shareit.item.dto.commentDtos;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentCreateDto {

    private String text;

}
