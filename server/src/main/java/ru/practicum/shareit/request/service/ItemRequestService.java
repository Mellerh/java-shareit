package ru.practicum.shareit.request.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;

import java.util.List;

@Service
public interface ItemRequestService {

    List<ItemRequestResponseDto> getAllRequestsByUserId(Long userId);

    List<ItemRequestResponseDto> getAllRequestsByOthers(Long userId);

    ItemRequestResponseDto getOneRequestByUserId(Long userId, Long requestId);

    ItemRequestResponseDto createNewRequest(Long userId, ItemRequestCreateDto createDto);

}
