package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO, получаемое при обновлении User
 */

@Data
@Builder
@AllArgsConstructor
public class UserUpdateDto {

    private Long id; /// возможно, мы не получаем id в UserUpdate. А только через путь URL. Тогда нужно удалить проверки
    /// посмотрим postman-тесты
    private String name;
    private String email;

}
