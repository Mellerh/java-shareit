package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Long userId = 0L;
    private final Map<Long, User> userMap = new HashMap<>();

    @Override
    public Collection<User> getAllUsers() {
        return userMap.values();
    }

    @Override
    public User getUserById(Long userId) {
        return userMap.get(userId);
    }

    @Override
    public User createUser(User userCreateDto) {

        return null;
    }

    @Override
    public User userUpdate(User userUpdateDto) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }
}
