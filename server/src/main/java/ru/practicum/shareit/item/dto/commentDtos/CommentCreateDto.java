package ru.practicum.shareit.item.dto.commentDtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Jacksonized
public class CommentCreateDto {

    private Long id;
    private String text;
    private Item item;
    private User author;
    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();

}
