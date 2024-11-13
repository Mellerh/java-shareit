package ru.practicum.shareit.item.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemShortDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;

import java.util.Collection;

@Service
public interface ItemService {

    ItemDto getItemById(Long itemId);

    Collection<ItemShortDto> getAllUserItems(Long userId);

    ItemDto addNewItem(Long userId, ItemCreateDto item);

    ItemDto updateItem(Long userId, Long itemId, ItemUpdateDto item);

    ItemDto getAvailableItem(String text);

}
