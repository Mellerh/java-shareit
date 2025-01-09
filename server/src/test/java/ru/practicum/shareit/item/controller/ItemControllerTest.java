package ru.practicum.shareit.item.controller;

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
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.dto.commentDtos.CommentCreateDto;
import ru.practicum.shareit.item.dto.commentDtos.CommentDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemCreateDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemResponseDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemUpdateDto;
import ru.practicum.shareit.item.service.ItemService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
@AutoConfigureMockMvc
class ItemControllerTest {

    @MockBean
    private ItemService itemService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;



    @Test
    @DisplayName("Провереяем get-запрос на /items и возвращение пустого листа")
    void getAllUserItemsWithEmptyList() throws Exception {

        long userId = 1L;

        Mockito
                .when(itemService.getAllUserItems(Mockito.anyLong()))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/items")
                                    .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));


        Mockito.verify(itemService, Mockito.times(1))
                .getAllUserItems(userId);

    }

    @Test
    @DisplayName("Провереяем get-запрос на /items и возвращение items")
    void getAllUserItemsWithNotEmptyList() throws Exception {

        long userId = 1L;
        List<ItemResponseDto> responseList = List.of(createItemResponseDto());

        Mockito
                .when(itemService.getAllUserItems(Mockito.anyLong()))
                .thenReturn(responseList);

        mockMvc.perform(get("/items")
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(responseList.get(0).getId()), Long.class)
                );

        Mockito.verify(itemService, Mockito.times(1))
                .getAllUserItems(userId);

    }

    @Test
    @DisplayName("Проверяем работу get-запроса на /items/{itemId}")
    void getItemById() throws Exception {
        long userId = 1L;
        long itemId = 1L;

        ItemResponseDto responseDto = createItemResponseDto();

        Mockito
                .when(itemService.getItemById(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(responseDto);

        mockMvc.perform(get("/items/1")
                        .header("X-Sharer-User-Id", 1))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id", is(responseDto.getId()), Long.class)
                );
    }


    @Test
    @DisplayName("Проверяем работу post-запроса на /items")
    void addNewItem() throws Exception {

        long userId = 1L;

        ItemCreateDto createDto = createItemCreateDto();
        ItemResponseDto responseDto = createItemResponseDto();

        Mockito
                .when(itemService.addNewItem(Mockito.anyLong(), Mockito.any()))
                .thenReturn(responseDto);

        mockMvc.perform(post("/items")
                        .content(mapper.writeValueAsString(createDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header("X-Sharer-User-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.name", equalTo(responseDto.getName()))
                );

        Mockito.verify(itemService, Mockito.times(1))
                .addNewItem(userId, createDto);
    }

    @Test
    @DisplayName("Проверям работу patch-запроса на /items/{itemId}")
    void updateItem() throws Exception {

        long userId = 1L;
        long itemId = 1L;
        ItemUpdateDto dto = createItemupdateDto();

        ItemResponseDto responseDto = createItemResponseDto();
        responseDto.setName(dto.getName());

        Mockito
                .when(itemService.updateItem(userId, itemId, dto))
                .thenReturn(responseDto);

        mockMvc.perform(patch("/items/1")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header("X-Sharer-User-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.name", equalTo(responseDto.getName()))
                );

        Mockito.verify(itemService, Mockito.times(1))
                .updateItem(userId, itemId, dto);
    }


    @Test
    @DisplayName("Проверям работу get-запроса /items/search")
    void getAvailableItemsByText() throws Exception {

        long userId = 1L;
        String text = "Phone";
        ItemResponseDto responseDto = createItemResponseDto();
        List<ItemResponseDto> list = List.of(responseDto);

        Mockito
                .when(itemService.getAvailableItemsByText(userId, text))
                .thenReturn(list);

        mockMvc.perform(get("/items/search")
                        .header("X-Sharer-User-Id", "1")
                        .param("text", text)
                )
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[0].id", equalTo(list.get(0).getId()), Long.class),
                        jsonPath("$[0].name", equalTo(list.get(0).getName()))
                );

        Mockito.verify(itemService, Mockito.times(1))
                .getAvailableItemsByText(userId, text);
    }

    @Test
    @DisplayName("Проверяем post-запрос на /items/{itemId}/comment")
    void createCommentForItem() throws Exception {

        long userId = 1L;
        long itemId = 1L;
        CommentCreateDto createDto = createCommentCreateDto();
        CommentDto commentDto = createCommentDto();

        Mockito
                .when(itemService.createCommentForItem(userId, itemId,
                        createDto))
                .thenReturn(commentDto);

        mockMvc.perform(post("/items/1/comment")
                .content(mapper.writeValueAsString(createDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .header("X-Sharer-User-Id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.id", is(commentDto.getId()), Long.class),
                jsonPath("$.text", equalTo(commentDto.getText()))
        );

        Mockito.verify(itemService, Mockito.times(1))
                .createCommentForItem(userId, itemId, createDto);
    }



    private ItemCreateDto createItemCreateDto() {
        return ItemCreateDto.builder()
                .name("Phone")
                .description("good phone")
                .available(true)
                .build();
    }

    private ItemUpdateDto createItemupdateDto() {
        return ItemUpdateDto.builder()
                .name("SomePhone")
                .description("desc of somePhone")
                .build();
    }

    private ItemResponseDto createItemResponseDto() {

        BookingShortDto lastBookingShortDto = BookingShortDto.builder()
                .id(1L)
                .bookerId(1L)
                .start(LocalDateTime.now().minusHours(10))
                .end(LocalDateTime.now().minusHours(5))
                .build();

        BookingShortDto nextBookingShortDto = BookingShortDto.builder()
                .id(1L)
                .bookerId(1L)
                .start(LocalDateTime.now().plusHours(1))
                .end(LocalDateTime.now().plusHours(2))
                .build();

        CommentDto commentDto = createCommentDto();

        return ItemResponseDto.builder()
                .id(1L)
                .name("Phone")
                .description("good phone")
                .available(true)
                .lastBooking(lastBookingShortDto)
                .nextBooking(nextBookingShortDto)
                .comments(java.util.List.of(commentDto))
                .build();
    }

    private CommentCreateDto createCommentCreateDto() {
        return CommentCreateDto.builder()
                .text("comment for Item")
                .build();
    }

    private CommentDto createCommentDto() {
        return CommentDto.builder()
                .id(1L)
                .text("comment for Item")
                .authorName("Author")
                .created(LocalDateTime.now())
                .build();
    }

}