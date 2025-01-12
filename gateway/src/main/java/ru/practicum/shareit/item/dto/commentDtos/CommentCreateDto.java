package ru.practicum.shareit.item.dto.commentDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Jacksonized
public class CommentCreateDto {

    @Null
    private Long id;
    @NotBlank
    private String text;
    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();

}
