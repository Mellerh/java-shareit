package ru.practicum.shareit.item.dto.commentDtos;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class CommentDtoJsonTest {

    private final JacksonTester<CommentDto> json;

    @Test
    @DisplayName("Тестируем корректную серриализацию CommentDto в Json-строку")
    void toCommentDto() throws Exception {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        CommentDto commentDto = CommentDto.builder()
                .id(1L)
                .text("some text")
                .authorName("Taras")
                .created(now)
                .build();

        JsonContent<CommentDto> result = json.write(commentDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.text").isEqualTo("some text");
        assertThat(result).extractingJsonPathStringValue("$.authorName").isEqualTo("Taras");
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo(formatter.format(now));

    }

}