package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.client.ItemRequestClient;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Locale;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemRequestController.class)
class ItemRequestControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ItemRequestClient requestClient;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("Проверяем корректную работу валидации gateway")
    void addNewRequestWithEmptyDescription() throws Exception {
        ItemRequestCreateDto createDto = createItemRequestCreateDto();

        mockMvc.perform(post("/requests")
                        .content(objectMapper.writeValueAsString(createDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .locale(Locale.ENGLISH)
                        .header("X-Sharer-User-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(requestClient,
                never()).createNewRequest(Mockito.anyLong(), Mockito.any());
    }

    private static ItemRequestCreateDto createItemRequestCreateDto() {
        return ItemRequestCreateDto.builder()
                .id(1L)
                .created(LocalDateTime.now())
                .build();
    }
}