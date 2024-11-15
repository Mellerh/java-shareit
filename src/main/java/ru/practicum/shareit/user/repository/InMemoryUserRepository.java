package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private Long userIdCounter = 0L;
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
    public User createUser(User user) {
        ++userIdCounter;
        user.setId(userIdCounter);
        userMap.put(userIdCounter, user);

        return user;
    }

    @Override
    public User userUpdate(User user) {
        // поскольку данные у нас сейчас хранятся в памяти, то перезаписывать User с помощью
        // userMap.put(userId, user) не нужно. Достаточно обновление полей UserService.userUpdate(), тк там мы получаем
        // User с помощью getById()
        return userMap.get(user.getId());
    }

    @Override
    public void deleteUser(Long userId) {
        userMap.remove(userId);
    }


    /**
     * провярем, занят ли email
     * если true - email занят
     */
    public boolean isUserEmailEngaged(Long userId, String email) {
        boolean isEmailEngaged = false;

        for (User userFromMap : userMap.values()) {

            if (userId != null && userId.equals(userFromMap.getId())) {
                continue;
            }

            if (email.equals(userFromMap.getEmail())) {
                isEmailEngaged = true;
                break;
            }
        }

        return isEmailEngaged;

    }



}
