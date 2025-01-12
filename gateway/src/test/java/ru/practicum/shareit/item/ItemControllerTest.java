package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.client.ItemClient;
import ru.practicum.shareit.item.dto.itemDtos.ItemCreateDto;

import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ItemClient itemClient;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("Проверяем корректную работу валидации gateway")
    void addItemWithBlankNameAndDescAndAvailable() throws Exception {
        ItemCreateDto createDto = createItemCreateDto();

        mockMvc.perform(post("/items")
                        .header("X-Sharer-User-Id", "1")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        Mockito.verify(itemClient, never())
                .addNewItem(Mockito.anyLong(), Mockito.any());
    }

    private static ItemCreateDto createItemCreateDto() {
        return ItemCreateDto.builder()
                .name("")
                .description("")
                .build();
    }
}