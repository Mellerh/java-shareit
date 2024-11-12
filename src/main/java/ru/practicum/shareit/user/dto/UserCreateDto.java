package ru.practicum.shareit.user.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO, получаемое при создании User
 */

@Data
public class UserCreateDto {

    @NotNull
    private String name;
    @NotNull
    private String email;

}
