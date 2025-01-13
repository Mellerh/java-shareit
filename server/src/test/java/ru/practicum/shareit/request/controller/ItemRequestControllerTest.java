package ru.practicum.shareit.request.controller;

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
import ru.practicum.shareit.item.dto.itemDtos.ItemResponseDto;
import ru.practicum.shareit.request.ItemRequestController;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ItemRequestController.class)
@AutoConfigureMockMvc
class ItemRequestControllerTest {

    @MockBean
    private ItemRequestService requestService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private final LocalDateTime now = LocalDateTime.now();

    @Test
    @DisplayName("Тестриуем работу get-запроса на /requests")
    void getAllRequestsByUserId() throws Exception {

        Long userId = 1L;
        ItemRequestResponseDto responseDto = createResponseDto();
        List<ItemRequestResponseDto> listDtos = List.of(responseDto);

        Mockito
                .when(requestService.getAllRequestsByUserId(Mockito.anyLong()))
                .thenReturn(listDtos);

        mockMvc.perform(get("/requests")
                .header("X-Sharer-User-Id", userId)
        ).andExpectAll(
                status().isOk(),
                jsonPath("$[0].id", is(listDtos.get(0).getId()), Long.class)
        );

        Mockito.verify(requestService, Mockito.times(1))
                .getAllRequestsByUserId(userId);

    }

    @Test
    @DisplayName("Тестриуем работу get-запроса на /requests/all")
    void getAllRequestsByOthers() throws Exception {

        Long userId = 1L;
        ItemRequestResponseDto responseDto = createResponseDto();
        List<ItemRequestResponseDto> listDtos = List.of(responseDto);

        Mockito
                .when(requestService.getAllRequestsByOthers(Mockito.anyLong()))
                .thenReturn(listDtos);

        mockMvc.perform(get("/requests/all")
                .header("X-Sharer-User-Id", userId)
        ).andExpectAll(
                status().isOk(),
                jsonPath("$[0].id", is(listDtos.get(0).getId()), Long.class)
        );

        Mockito.verify(requestService, Mockito.times(1))
                .getAllRequestsByOthers(userId);

    }

    @Test
    @DisplayName("Тестриуем работу get-запроса на /requests/{requestId}")
    void getOneRequestByUserId() throws Exception {

        long userId = 1L;
        long requestId = 1L;
        ItemRequestResponseDto responseDto = createResponseDto();

        Mockito
                .when(requestService.getOneRequestByUserId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(responseDto);

        mockMvc.perform(get("/requests/1")
                .header("X-Sharer-User-Id", userId)
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.id", is(responseDto.getId()), Long.class)
        );

        Mockito.verify(requestService, Mockito.times(1))
                .getOneRequestByUserId(userId, requestId);
    }

    @Test
    @DisplayName("Тестриуем работу post-запроса на /requests")
    void createNewRequest() throws Exception {
        long userId = 1L;
        ItemRequestCreateDto createDto = createRequestDto();
        ItemRequestResponseDto responseDto = createResponseDto();

        Mockito
                .when(requestService.createNewRequest(userId, createDto))
                .thenReturn(responseDto);

        mockMvc.perform(post("/requests")
                .content(mapper.writeValueAsString(createDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .header("X-Sharer-User-Id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.id", is(responseDto.getId()), Long.class)
        );

        Mockito.verify(requestService, Mockito.times(1))
                .createNewRequest(userId, createDto);

    }


    private ItemRequestCreateDto createRequestDto() {
        return ItemRequestCreateDto.builder()
                .description("запрос")
                .created(now)
                .build();
    }

    private ItemRequestResponseDto createResponseDto() {
        ItemResponseDto item = ItemResponseDto.builder()
                .id(1L)
                .name("Some")
                .description("Some desc")
                .available(true)
                .build();

        return ItemRequestResponseDto.builder()
                .id(1L)
                .description("Some desc")
                .created(now)
                .items(List.of(item))
                .build();
    }
}