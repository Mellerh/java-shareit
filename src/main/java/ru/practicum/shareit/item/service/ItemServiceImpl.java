package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.itemDtos.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {


    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;

    @Override
    public ItemDto getItemById(Long userId, Long itemId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User с id " + userId + " не найден.");
        }

        Item item = itemRepository.getItemById(itemId);
        if (item == null) {
            throw new NotFoundException("Item с id " + itemId + " не найдена.");
        }

        if (!Objects.equals(user.getId(), item.getOwner().getId())) {
            throw new NotFoundException("Item с id " + itemId + " не принадлежит User с id " + userId);
        }

        return itemMapper.toItemDto(item, user);
    }

    @Override
    public Collection<ItemShortDto> getAllUserItems(Long userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User с id " + userId + " не найден.");
        }

        return itemRepository.getAllUserItems(userId).stream()
                .map(item -> itemMapper.toShortDto(item))
                .toList();
    }

    @Override
    public ItemDto addNewItem(Long userId, ItemCreateDto itemDto) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User с id " + userId + " не найден.");
        }


        Item item = itemMapper.toItemModel(itemDto, user);

        Item itemWithId = itemRepository.addNewItem(userId, item);

        return itemMapper.toItemDto(itemWithId, user);
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemUpdateDto itemUpdate) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User с id " + userId + " не найден.");
        }

        Item item = itemRepository.getItemById(itemId);
        if (item == null) {
            throw new NotFoundException("Item с id " + itemId + " не найдена.");
        }

        if (!Objects.equals(user.getId(), item.getOwner().getId())) {
            throw new NotFoundException("Item с id " + itemId + " не принадлежит User с id " + userId);
        }

        if (itemUpdate.getName() != null) {
            item.setName(itemUpdate.getName());
        }

        if (itemUpdate.getDescription() != null) {
            item.setDescription(itemUpdate.getDescription());
        }

        if (itemUpdate.getAvailable() != null) {
            item.setAvailable(itemUpdate.getAvailable());
        }

        return itemMapper.toItemDto(itemRepository.updateItem(userId, itemId, item), user);

    }

    @Override
    public Collection<ItemDto> getAvailableItemsByText(String text) {
        // поскольку в sql есть оператор Like, то передадим всю логику поиска в itemRepository
        if (text.isEmpty()) {
            return new ArrayList<>();
        }

        return itemRepository.getAvailableItemsByText(text).stream()
                .map(item -> itemMapper.toItemDto(item, item.getOwner()))
                .toList();

    }


    private User findUserOrThrowExp(Long userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User с id " + userId + " не найден.");
        }
        return user;
    }

    private Item findItemOrThrowExp(Long itemId) {
        Item item = itemRepository.getItemById(itemId);
        if (item == null) {
            throw new NotFoundException("Item с id " + itemId + " не найдена.");
        }
        return item;
    }

}
