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

    @Override
    public ItemDto getItemById(Long userId, Long itemId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("User с id " + userId + " не найден."));

        Item item = itemRepository.findById(itemId).orElseThrow(()
                -> new NotFoundException("Item с id " + itemId + " не найден."));


        if (!Objects.equals(user.getId(), item.getOwner().getId())) {
            throw new NotFoundException("Item с id " + itemId + " не принадлежит User с id " + userId);
        }

        return ItemMapper.toItemDto(item, user);
    }

    @Override
    public Collection<ItemShortDto> getAllUserItems(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("User с id " + userId + " не найден."));

        return itemRepository.findAllByOwnerId(user.getId()).stream()
                .map(item -> ItemMapper.toShortItemDto(item))
                .toList();
    }

    @Override
    public ItemShortDto addNewItem(Long userId, ItemCreateDto itemDto) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("User с id " + userId + " не найден."));

        Item item = ItemMapper.toItemModel(itemDto, user);

        return ItemMapper.toShortItemDto(itemRepository.save(item));
    }

    @Override
    public ItemShortDto updateItem(Long userId, Long itemId, ItemUpdateDto itemUpdate) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("User с id " + userId + " не найден."));

        // возможно, тут надо получать id из itemUpdateDto
        Item item = itemRepository.findById(itemId).orElseThrow(()
                -> new NotFoundException("Item с id " + itemId + " не найдена."));


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

        item.setOwner(user);

        return ItemMapper.toShortItemDto(itemRepository.save(item));

    }

    @Override
    public Collection<ItemDto> getAvailableItemsByText(String text) {
        // поскольку в sql есть оператор Like, то передадим всю логику поиска в itemRepository
        if (text.isEmpty()) {
            return new ArrayList<>();
        }

        return itemRepository.searchByText(text).stream()
                .map(item -> ItemMapper.toItemDto(item, item.getOwner()))
                .toList();
    }


}
