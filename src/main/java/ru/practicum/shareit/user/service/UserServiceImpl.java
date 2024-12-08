package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserMapper.toUserDto(user))
                .toList();
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Пользователь с id " + userId + " не найден."));

        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto createUser(UserCreateDto userCreateDto) {
        if (userRepository.existsByEmail(userCreateDto.getEmail())) {
            throw new DuplicatedDataException("Пользователь с " + userCreateDto.getEmail() + " уже существует.");
        }

        User user = UserMapper.toUserModel(userCreateDto);

        return UserMapper.toUserDto(userRepository.save(user));

    }

    @Override
    public UserDto userUpdate(Long userId, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Пользователь с id " + userId + " не найден."));

        if (userUpdateDto.getName() != null) {
            user.setName(userUpdateDto.getName());
        }

        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }

        return UserMapper.toUserDto(userRepository.save(user));

    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
