package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.client.ItemClient;
import ru.practicum.shareit.item.dto.commentDtos.CommentCreateDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemCreateDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemUpdateDto;

@RestController
@RequestMapping("/items")
@Validated
@RequiredArgsConstructor
public class ItemController {

    // X-Sharer-User-Id - это заголовок, из которого мы получаем userId
    private final String userIdFromHeader = "X-Sharer-User-Id";
    private final ItemClient itemService;

    @GetMapping
    public ResponseEntity<Object> getAllUserItems(@RequestHeader(userIdFromHeader) Long userId) {
        return itemService.getAllUserItems(userId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(@RequestHeader(userIdFromHeader) Long userId,
                               @PathVariable Long itemId) {
        return itemService.getItemById(userId, itemId);
    }

    @PostMapping
    public ResponseEntity<Object> addNewItem(@RequestHeader(userIdFromHeader) Long userId,
                                      @Valid @RequestBody ItemCreateDto itemCreateDto) {
        return itemService.addNewItem(userId, itemCreateDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@RequestHeader(userIdFromHeader) Long userId,
                                      @PathVariable Long itemId,
                                      @Valid @RequestBody ItemUpdateDto itemUpdateDto) {
        return itemService.updateItem(userId, itemId, itemUpdateDto);
    }

    /**
     * поиск вещи потенциальным арендатором
     */
    @GetMapping("/search")
    public ResponseEntity<Object> getAvailableItemsByText(@RequestHeader(userIdFromHeader) Long userId,
                                                       @RequestParam(required = false) String text) {
        return itemService.getAvailableItemsByText(userId, text);
    }

    /**
     * добавляем комментарий для вещи
     */
    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createCommentForItem(@RequestHeader(userIdFromHeader) Long userId,
                                           @PathVariable Long itemId,
                                           @RequestBody CommentCreateDto createDto) {
        return itemService.createCommentForItem(userId, itemId, createDto);
    }

}
