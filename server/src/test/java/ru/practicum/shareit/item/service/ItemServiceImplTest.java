package ru.practicum.shareit.item.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.exception.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.commentDtos.CommentCreateDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemCreateDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemResponseDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemUpdateDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@Transactional
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemServiceImplTest {

    private final ItemService itemService;
    private final UserService userService;


    @Test
    @DisplayName("Проверяем корректное получение Item из таблицы по id")
    void getItemByCorrectId() {

        long userId = 1L;
        long itemId = 1L;

        ItemResponseDto item = itemService.getItemById(userId, itemId);
        Assertions.assertEquals("Phone", item.getName());
    }

    @Test
    @DisplayName("Проверяем получение ошибки при поиске Item из таблицы по несуществующему id")
    void getItemByInCorrectId() {

        long userId = 1L;
        long itemId = 2L;

        Assertions.assertThrows(NotFoundException.class,
                () -> itemService.getItemById(userId, itemId));
    }

    @Test
    @DisplayName("Проверяем получение всех item пользователя")
    void getAllUserItems() {

        long userId = 1L;

        List<ItemResponseDto> dtoList = itemService.getAllUserItems(userId);

        Assertions.assertEquals(1, dtoList.size());
        Assertions.assertEquals("good phone", dtoList.get(0).getDescription());

    }

    @Test
    @DisplayName("Проверяем создание нового item")
    void addNewItem() {

        long userId = 1L;

        ItemCreateDto createDto = ItemCreateDto.builder()
                .name("Ipad")
                .description("desc of Ipad")
                .available(true)
                .build();


        ItemResponseDto responseDto = itemService.addNewItem(userId, createDto);
        Assertions.assertEquals(2L, responseDto.getId());
        Assertions.assertEquals("Ipad", responseDto.getName());
    }

    @Test
    @DisplayName("Проверяем апдейт item")
    void updateItem() {

        long userId = 1L;
        long itemId = 1L;
        ItemUpdateDto updateDto = ItemUpdateDto.builder().available(false).build();

        ItemResponseDto responseDto = itemService.updateItem(userId, itemId, updateDto);
        Assertions.assertEquals(1L, responseDto.getId());
        Assertions.assertEquals(false, responseDto.getAvailable());
    }

    @Test
    @DisplayName("Проверяем корректное получение item по названию")
    void getAvailableItemsByText() {
        long userId = 1L;
        String text = "Phone";

        List<ItemResponseDto> dtoList = itemService.getAvailableItemsByText(userId, text);
        Assertions.assertEquals("Phone", dtoList.get(0).getName());
    }

    @Test
    @DisplayName("Проверяем получение пустого списка при передаче пустого значение в text")
    void getEmptyListByBlankText() {
        long userId = 1L;
        String text = "";

        List<ItemResponseDto> dtoList = itemService.getAvailableItemsByText(userId, text);
        Assertions.assertEquals(0, dtoList.size());
    }


    @Test
    @DisplayName("Проверяем создание комментария для item")
    void createCommentForItem() {

        long userId = 1L;
        long itemId = 1L;
        CommentCreateDto commentDto = CommentCreateDto.builder().text("классный phone").build();
        itemService.createCommentForItem(userId, itemId, commentDto);

        ItemResponseDto itemResponseDto = itemService.getItemById(userId, itemId);
        Assertions.assertEquals(2, itemResponseDto.getComments().size());
        Assertions.assertEquals("классный phone", itemResponseDto.getComments().get(0).getText());

    }
}