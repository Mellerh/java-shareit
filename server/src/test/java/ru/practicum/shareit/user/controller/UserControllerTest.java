package ru.practicum.shareit.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    @Test
    @DisplayName("Тестриуем работу get-запроса на /users и получение пустого списка")
    void getAllUsersWithEmpyList() throws Exception {

        when(userService.getAllUsers())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]")
                );

        Mockito.verify(userService, Mockito.times(1))
                .getAllUsers();
    }

    @Test
    @DisplayName("Тестриуем работу get-запроса на /users и получение списка с пользователями")
    void getAllUsers() throws Exception {

        List<UserDto> userList = List.of(createUserDto());

        Mockito
                .when(userService.getAllUsers())
                .thenReturn(userList);

        mockMvc.perform(
                        get("/users")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(status().isOk(),
                        jsonPath("$[0].id", is(userList.get(0).getId()), Long.class)
                );

        Mockito.verify(userService, Mockito.times(1))
                .getAllUsers();
    }

    @Test
    @DisplayName("Тестируем работу get-запроса на /users/{userId}")
    void getUserById() throws Exception {

        long userId = 1L;
        UserDto userDto = createUserDto();

        Mockito
                .when(userService.getUserById(userId))
                .thenReturn(userDto);

        mockMvc.perform(get("/users/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(),
                        jsonPath("$.name", equalTo("Taras"))
                );

        Mockito.verify(userService, Mockito.times(1))
                .getUserById(userId);
    }

    @Test
    void createUser() {
    }

    @Test
    void userUpdate() {
    }

    @Test
    void deleteUser() {
    }


    private UserCreateDto createUserCreateDto() {
        return UserCreateDto
                .builder()
                .name("Taras")
                .email("v@yandex.ru")
                .build();
    }

    private UserUpdateDto createUserUpdateDto() {
        return UserUpdateDto
                .builder()
                .name("Vasya")
                .email("vas@yandx.ru")
                .build();
    }

    private UserDto createUserDto() {
        return UserDto
                .builder()
                .id(1L)
                .name("Taras")
                .email("v@yandex.ru")
                .build();
    }

}