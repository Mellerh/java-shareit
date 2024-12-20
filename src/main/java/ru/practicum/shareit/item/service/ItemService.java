package ru.practicum.shareit.item.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.commentDtos.CommentCreateDto;
import ru.practicum.shareit.item.dto.commentDtos.CommentDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemCreateDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemResponseDto;
import ru.practicum.shareit.item.dto.itemDtos.ItemUpdateDto;

import java.util.Collection;

@Service
public interface ItemService {

    ItemResponseDto getItemById(Long userId, Long itemId);

    Collection<ItemResponseDto> getAllUserItems(Long userId);

    ItemResponseDto addNewItem(Long userId, ItemCreateDto item);

    ItemResponseDto updateItem(Long userId, Long itemId, ItemUpdateDto item);

    Collection<ItemResponseDto> getAvailableItemsByText(Long userId, String text);

    CommentDto createCommentForItem(Long userId, Long itemId, CommentCreateDto createDto);

}
