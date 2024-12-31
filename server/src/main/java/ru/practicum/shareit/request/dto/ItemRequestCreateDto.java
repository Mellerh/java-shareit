package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Data
public class ItemRequestCreateDto {

    private String description;

    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();

}
