package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.dto.itemDtos.ItemResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class ItemRequestResponseDto {

    private Long id;
    private String description;
    private LocalDateTime created;
    private List<ItemResponseDto> items;

}
