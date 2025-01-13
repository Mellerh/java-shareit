package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@Data
public class ItemRequestCreateDto {

    private Long id;
    private String description;
    private LocalDateTime created;

}
