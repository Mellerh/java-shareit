package ru.practicum.shareit.user.dto;

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
class UserDtoJsonTest {

    private final JacksonTester<UserDto> json;

    @Test
    @DisplayName("Тестируем корректную серриализацию UserDto в Json-строку")
    void toUserDto() throws Exception {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .name("Taras")
                .email("t@yandex.ru")
                .build();


        JsonContent<UserDto> result = json.write(userDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("Taras");
        assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo("t@yandex.ru");

    }

}