package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.itemDtos.ItemCreateDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemShortDto;
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


    @GetMapping("/{itemId}")
    public ItemDto getItemById(@RequestHeader(userIdFromHeader) Long userId,
                               @PathVariable Long itemId) {
        return itemService.getItemById(userId, itemId);
    }

    @GetMapping
    public Collection<ItemShortDto> getAllUserItems(@RequestHeader(userIdFromHeader) Long userId) {
        return itemService.getAllUserItems(userId);
    }

    @PostMapping
    public ItemDto addNewItem(@RequestHeader(userIdFromHeader) Long userId,
                              @Valid @RequestBody ItemCreateDto itemCreateDto) {
        return itemService.addNewItem(userId, itemCreateDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader(userIdFromHeader) Long userId,
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

}
