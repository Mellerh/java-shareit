package ru.practicum.shareit.item.dto.commentDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CommentUpdateDto {

    private String text;
    private Long itemId;

}
