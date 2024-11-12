package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;

@Repository
public interface UserRepository {

    Collection<User> getAllUsers();

    User getUserById(Long userId);

    User createUser(User userCreateDto);

    User userUpdate(User userUpdateDto);

    void deleteUser(Long userId);

}
