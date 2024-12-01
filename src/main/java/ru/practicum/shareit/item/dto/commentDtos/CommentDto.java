package ru.practicum.shareit.item.dto.commentDtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CommentDto {

    private Long id;
    private String text;

    private String authorName;

}
