package ru.practicum.shareit.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.exception.exceptions.DuplicatedDataException;
import ru.practicum.shareit.exception.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;

import java.util.List;

@Transactional
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserServiceImplTest {

    private final UserService userService;

    @Test
    @DisplayName("Проверяем коректное получение всех User из БД")
    void getAllUsers() {

        List<UserDto> dtoList = userService.getAllUsers();

        Assertions.assertEquals(1, dtoList.size());
        Assertions.assertEquals("Taras", dtoList.get(0).getName());

    }

    @Test
    @DisplayName("Проверяем получение сущетсвующего User")
    void getUserByCorrectId() {

        long userId = 1L;

        UserDto userDto = userService.getUserById(userId);
        Assertions.assertEquals("Taras", userDto.getName());

    }

    @Test
    @DisplayName("Проверяем получение ошибки при получение несущетсвующего User")
    void getUserByInCorrectId() {

        long userId = 2L;

        Assertions.assertThrows(NotFoundException.class,
                () -> userService.getUserById(userId));

    }

    @Test
    @DisplayName("Проверяем создание нового пользователя с уникальным Email")
    void createUserWithUnique() {

        UserCreateDto createDto = UserCreateDto
                .builder()
                .name("Sasha")
                .email("s@yandex.ru")
                .build();

        UserDto userDto = userService.createUser(createDto);
        Assertions.assertEquals(2, userDto.getId());
        Assertions.assertEquals("Sasha", userDto.getName());
        
    }

    @Test
    @DisplayName("Проверяем создание нового пользователя с существующем Email")
    void createUserWithUnUniqueEmail() {

        UserCreateDto createDto = UserCreateDto
                .builder()
                .name("Sasha")
                .email("vasilenko.taras2015@yandex.ru")
                .build();

        Assertions.assertThrows(DuplicatedDataException.class, ()
                -> userService.createUser(createDto));
    }

    @Test
    @DisplayName("Проверяем обновление пользователя")
    void userUpdate() {

        long userId = 1L;

        UserUpdateDto userUpdateDto = UserUpdateDto.builder().email("taras2015@yandex.ru").build();

        UserDto userDto = userService.userUpdate(userId, userUpdateDto);
        Assertions.assertEquals(1, userDto.getId());
        Assertions.assertEquals(userUpdateDto.getEmail(), userDto.getEmail());
    }

    @Test
    @DisplayName("Проверяем удаление пользователя")
    void deleteUser() {
        long userId = 1L;

        List<UserDto> userDtos = userService.getAllUsers();
        Assertions.assertEquals(1, userDtos.size());

        userService.deleteUser(userId);
        List<UserDto> userDtosAfterDelete = userService.getAllUsers();
        Assertions.assertEquals(0, userDtosAfterDelete.size());
    }

}