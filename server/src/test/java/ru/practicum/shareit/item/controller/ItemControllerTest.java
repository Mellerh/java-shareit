package ru.practicum.shareit.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.item.dto.commentDtos.CommentCreateDto;
import ru.practicum.shareit.item.dto.commentDtos.CommentDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemCreateDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemResponseDto;
import ru.practicum.shareit.item.service.ItemService;

import java.time.LocalDateTime;


@WebMvcTest(ItemControllerTest.class)
@AutoConfigureMockMvc
class ItemControllerTest {

    @MockBean
    private ItemService itemService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;



    @Test
    void getAllUserItems() {
    }

    @Test
    void getItemById() {
    }

    @Test
    void addNewItem() {
    }

    @Test
    void updateItem() {
    }

    @Test
    void getAvailableItemsByText() {
    }

    @Test
    void createCommentForItem() {
    }

    private ItemCreateDto createItemCreateDto() {
        return ItemCreateDto.builder()
                .name("Phone")
                .description("good phone")
                .available(true)
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
        return CommentCreateDto.builder().text("comment for Item").build();
    }

    private CommentDto createCommentDto() {
        return CommentDto.builder()
                .text("comment for Item")
                .authorName("Author")
                .created(LocalDateTime.now())
                .build();
    }

}