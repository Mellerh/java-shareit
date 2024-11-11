package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateDto {

    @NotNull
    private Long id;
    private String name;
    private String email;

}
