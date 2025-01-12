package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.shareit.user.client.UserClient;
import ru.practicum.shareit.user.dto.UserCreateDto;

import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserClient userClient;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("Проверяем корректную работу валидации gateway")
    void createUserWithBlankName() throws Exception {
        UserCreateDto createDto = createUserCreateDto();

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpectAll(
                        status().isInternalServerError()
                );

        Mockito.verify(userClient, never())
                .createUser(Mockito.any());
    }


    private static UserCreateDto createUserCreateDto() {
        return UserCreateDto.builder()
                .email("y@yandex.ru")
                .build();
    }

}