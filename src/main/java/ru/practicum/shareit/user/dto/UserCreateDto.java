package ru.practicum.shareit.user.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCreateDto {

    @NotNull
    private String name;
    @NotNull
    private String email;

}
