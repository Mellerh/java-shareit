package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.exceptions.DataConflictException;
import ru.practicum.shareit.exception.exceptions.DuplicatedDataException;
import ru.practicum.shareit.exception.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.dto.UserUpdateDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.getAllUsers().stream()
                .map(user -> userMapper.toUserDto(user))
                .toList();
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден.");
        }

        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto createUser(UserCreateDto userCreateDto) {

        User user = userMapper.toUserModel(userCreateDto);

        try {
            return userMapper.toUserDto(userRepository.createUser(user));
        } catch (DataConflictException e) {
            throw new DuplicatedDataException("Пользователь с email " + userCreateDto.getEmail() + " уже существует.");
        }

    }

    @Override
    public UserDto userUpdate(Long userId, UserUpdateDto userUpdateDto) {

        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + userUpdateDto + " не найден.");
        }

        if (userUpdateDto.getName() != null) {
            user.setName(userUpdateDto.getName());
        }

        // обновляем email. проверяем не используется ли email у других User
        if (userUpdateDto.getEmail() != null && userUpdateDto.getEmail() != user.getEmail()) {
            user.setEmail(userUpdateDto.getEmail());
        }

        try {
            return userMapper.toUserDto(userRepository.userUpdate(user));
        } catch (DataConflictException e) {
            throw new DuplicatedDataException("Пользователь с email " + userUpdateDto.getEmail() + " уже существует.");
        }

    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден.");
        }

        userRepository.deleteUser(userId);
    }
}
