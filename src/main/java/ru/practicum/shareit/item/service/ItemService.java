package ru.practicum.shareit.item.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.itemDtos.ItemCreateDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemShortDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemUpdateDto;

import java.util.Collection;

@Service
public interface ItemService {

    ItemDto getItemById(Long userId, Long itemId);

    Collection<ItemShortDto> getAllUserItems(Long userId);

    ItemShortDto addNewItem(Long userId, ItemCreateDto item);

    ItemShortDto updateItem(Long userId, Long itemId, ItemUpdateDto item);

    Collection<ItemDto> getAvailableItemsByText(String text);

}
