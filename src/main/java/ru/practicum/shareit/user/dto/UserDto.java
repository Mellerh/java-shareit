package ru.practicum.shareit.user.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.dto.markerInterfaces.Update;

/**
 * DTO со всеми полями
 */
@Data
@Builder
@AllArgsConstructor
public class UserDto {

    @NotNull(groups = {Update.class})
    @Positive(groups = {Update.class})
    private Long id;

    @NotBlank(message = "name не может быть пустым.")
    private String name;

    @NotBlank
    @Email(message = "email должен быть настоящим.")
    private String email;

}
