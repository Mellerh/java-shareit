package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemShortDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    /// TODO как один из вариантов связи вещей с пользователем - это связанная хеш-таблица - Map<UserId, List<Item>>.
    /// но это как один из вариантов
    @Override
    public ItemDto getItemById(Long itemId) {
        return null;
    }

    @Override
    public Collection<ItemShortDto> getAllUserItems(Long userId) {
        return null;
    }

    @Override
    public ItemDto addNewItem(Long userId, ItemCreateDto item) {
        return null;
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemUpdateDto item) {
        return null;
    }

    @Override
    public Collection<ItemDto> getAvailableItems(String text) {
        return null;
    }
}
