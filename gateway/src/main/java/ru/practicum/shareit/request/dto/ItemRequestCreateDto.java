package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@Data
public class ItemRequestCreateDto {

    private Long id;

    @NotBlank
    private String description;
    @PastOrPresent
    private LocalDateTime created;

}
