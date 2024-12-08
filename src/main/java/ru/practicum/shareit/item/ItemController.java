package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.commentDtos.CommentCreateDto;
import ru.practicum.shareit.item.dto.commentDtos.CommentDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemCreateDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemResponseDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemUpdateDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@Validated
@RequiredArgsConstructor
public class ItemController {

    // X-Sharer-User-Id - это заголовок, из которого мы получаем userId
    private final String userIdFromHeader = "X-Sharer-User-Id";
    private final ItemService itemService;

    @GetMapping
    public Collection<ItemResponseDto> getAllUserItems(@RequestHeader(userIdFromHeader) Long userId) {
        return itemService.getAllUserItems(userId);
    }


    @GetMapping("/{itemId}")
    public ItemResponseDto getItemById(@RequestHeader(userIdFromHeader) Long userId,
                               @PathVariable Long itemId) {
        return itemService.getItemById(userId, itemId);
    }
    

    @PostMapping
    public ItemResponseDto addNewItem(@RequestHeader(userIdFromHeader) Long userId,
                                      @Valid @RequestBody ItemCreateDto itemCreateDto) {
        return itemService.addNewItem(userId, itemCreateDto);
    }

    @PatchMapping("/{itemId}")
    public ItemResponseDto updateItem(@RequestHeader(userIdFromHeader) Long userId,
                                      @PathVariable Long itemId,
                                      @Valid @RequestBody ItemUpdateDto itemUpdateDto) {
        return itemService.updateItem(userId, itemId, itemUpdateDto);
    }

    /**
     * поиск вещи потенциальным арендатором
     */
    @GetMapping("/search")
    public Collection<ItemDto> getAvailableItemsByText(@RequestHeader(userIdFromHeader) Long userId,
                                                       @RequestParam(required = false) String text) {
        return itemService.getAvailableItemsByText(text);
    }


    /**
     * добавляем комментарий для вещи
     */
    @PostMapping("/{itemId}/comment")
    public CommentDto createCommentForItem(@RequestHeader(userIdFromHeader) Long userId,
                                           @PathVariable Long itemId,
                                           @Valid @RequestBody CommentCreateDto createDto) {
        return itemService.createCommentForItem(userId, itemId, createDto);
    }

}
