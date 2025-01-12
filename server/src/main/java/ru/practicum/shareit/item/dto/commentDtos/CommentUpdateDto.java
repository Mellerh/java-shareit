package ru.practicum.shareit.item.dto.commentDtos;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentUpdateDto {

    private String text;
    private Long itemId;

}
