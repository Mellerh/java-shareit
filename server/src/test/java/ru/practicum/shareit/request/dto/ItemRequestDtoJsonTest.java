package ru.practicum.shareit.request.dto;

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
class ItemRequestDtoJsonTest {

    private final JacksonTester<ItemRequestDto> json;

    @Test
    @DisplayName("Тестируем корректную серриализацию ItemRequestDto в Json-строку")
    void toRequestDto() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime now = LocalDateTime.now();

        ItemRequestDto dtoRe = ItemRequestDto.builder()
                .id(1L)
                .description("some desc")
                .created(now)
                .build();

        JsonContent<ItemRequestDto> result = json.write(dtoRe);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo(dtoRe.getDescription());
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo(formatter.format(now));

    }


}