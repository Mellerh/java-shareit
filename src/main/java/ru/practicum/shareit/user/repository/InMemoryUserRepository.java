package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.exceptions.DataConflictException;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private Long userId = 0L;
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
        // если email нового пользователя уже занят, выбросим исключение
        isUserEmailEngaged(user);

        ++userId;
        user.setId(userId);
        userMap.put(userId, user);

        return user;
    }

    @Override
    public User userUpdate(User user) {
        isUserEmailEngaged(user);
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
     */
    private void isUserEmailEngaged(User user) {
        for (User userFromMap : userMap.values()) {

            if (user.getId() != null && user.getId().equals(userFromMap.getId())) {
                continue;
            }

            if (user.getEmail().equals(userFromMap.getEmail())) {
                throw new DataConflictException("Email уже используется.");
            }
        }

    }



}
