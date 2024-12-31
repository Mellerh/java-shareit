package ru.practicum.shareit.user.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

}
