package ru.practicum.shareit.user.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO, получаемое при создании User
 */

@Data
@Builder
@AllArgsConstructor
public class UserCreateDto {

    private String name;

    private String email;

}
