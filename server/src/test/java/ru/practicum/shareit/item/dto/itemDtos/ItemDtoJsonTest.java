package ru.practicum.shareit.item.dto.itemDtos;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemDtoJsonTest {

    private final JacksonTester<ItemDto> json;

    @Test
    @DisplayName("Тестируем корректную серриализацию ItemDto в Json-строку")
    void toItemDto() throws Exception {

        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .name("phone")
                .description("desc of phone")
                .available(true)
                .build();

        JsonContent<ItemDto> result = json.write(itemDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("phone");
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("desc of phone");
        assertThat(result).extractingJsonPathBooleanValue("$.available").isEqualTo(true);

    }

}