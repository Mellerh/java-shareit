package ru.practicum.shareit.item.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.*;

@Repository
public class InMemoryItemRepository implements ItemRepository {

    private Long itemIdCounter = 0L;

    private final Map<Long, Item> itemMap = new HashMap<>();
    private final Map<User, List<Item>> userItems = new HashMap<>();

    @Autowired
    private UserRepository userRepository;

    @Override
    public Item getItemById(Long itemId) {
        return itemMap.get(itemId);
    }

    @Override
    public Collection<Item> getAllUserItems(Long userId) {
        User user = userRepository.getUserById(userId);

        return userItems.get(user);
    }

    @Override
    public Item addNewItem(Long userId, Item item) {
        ++itemIdCounter;
        item.setId(itemIdCounter);
        itemMap.put(itemIdCounter, item);

        User user = userRepository.getUserById(userId);

        if (userItems.get(user) == null) {
            List<Item> itemList = new ArrayList<>();

            itemList.add(item);
            userItems.put(user, itemList);
        } else {
            userItems.get(user).add(item);
        }


        return item;
    }

    @Override
    public Item updateItem(Long userId, Long itemId, Item item) {
        // так как у нас item хранится в памяти, то его изменение в itemService изменит его и в itemMap.
        // поэтому мы просто вернём item по его id
        return itemMap.get(item.getId());
    }

    @Override
    public Collection<Item> getAvailableItemsByText(String text) {
        String loweredText = text.toLowerCase();

        return itemMap.values().stream()
                .filter(item -> item.getIs_available() != null)
                .filter(item -> item.getIs_available())
                .filter(item -> item.getName().toLowerCase().contains(loweredText)
                        || item.getDescription().toLowerCase().contains(loweredText))
                .toList();

    }
}
