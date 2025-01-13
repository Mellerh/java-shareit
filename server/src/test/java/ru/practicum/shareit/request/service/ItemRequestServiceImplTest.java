package ru.practicum.shareit.request.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.exception.exceptions.NotFoundException;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemRequestServiceImplTest {

    private final ItemRequestServiceImpl requestService;

    @Test
    @DisplayName("Проверяем коректное получение List<ItemRequest> из таблицы по ID пользователя")
    void getAllRequestsByUserId() throws Exception {

        long userId = 1L;

        List<ItemRequestResponseDto> dtoList = requestService.getAllRequestsByUserId(userId);
        Assertions.assertEquals("request on item", dtoList.get(0).getDescription());

    }

    @Test
    @DisplayName("Проверяем коректное получение пустогго списка List<ItemRequest>")
    void getAllRequestsByOthers() {

        long userId = 1L;

        List<ItemRequestResponseDto> dtoList = requestService.getAllRequestsByOthers(userId);
        Assertions.assertEquals(0, dtoList.size());

    }

    @Test
    @DisplayName("Проверяем коректное получение ItemRequest по ID")
    void getOneRequestByUserId() {

        long userId = 1L;
        long requestId = 1L;

        ItemRequestResponseDto responseDto = requestService.getOneRequestByUserId(userId, requestId);
        Assertions.assertEquals("request on item", responseDto.getDescription());

    }

    @Test
    @DisplayName("Проверяем получение ошибки при запросе ItemRequest по несуществующему ID")
    void getExceptionByIncorrectRequestId() {
        long userId = 1L;
        long requestId = 2L;

        Assertions.assertThrows(NotFoundException.class, ()
                -> requestService.getOneRequestByUserId(userId, requestId));
    }


    @Test
    @DisplayName("Создаём новый ItemRequest в БД")
    void createNewRequest() {
        long userId = 1L;
        LocalDateTime now = LocalDateTime.now();
        ItemRequestCreateDto createDto = ItemRequestCreateDto.builder()
                .description("запрос на item-1")
                .created(now)
                .build();

        ItemRequestResponseDto responseDto = requestService.createNewRequest(userId, createDto);

        Assertions.assertEquals(2, responseDto.getId());
        Assertions.assertEquals(createDto.getDescription(), responseDto.getDescription());

    }
}