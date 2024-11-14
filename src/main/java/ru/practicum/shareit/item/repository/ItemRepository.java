package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

@Repository
public interface ItemRepository {

    Item getItemById(Long itemId);

    Collection<Item> getAllUserItems(Long userId);

    Item addNewItem(Long userId, Item item);

    Item updateItem(Long userId, Long itemId, Item item);

    Collection<Item> getAvailableItems(String text);

}
