package ru.practicum.shareit.item.dto.commentDtos;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@Component
public class CommentMapper {

    public Comment toCommentModel(CommentCreateDto commentDto, Item item, User user) {
        return Comment.builder()
                .text(commentDto.getText())
                .item(item)
                .user(user)
                .build();
    }

    public CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getUser().getName())
                .build();
    }

}
