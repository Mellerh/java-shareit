package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private final String userIdFromHeader = "X-Sharer-User-Id";
    private final ItemRequestService requestService;

    @GetMapping
    public Collection<ItemRequestResponseDto> getAllRequestsByUserId(@RequestHeader(userIdFromHeader) Long userId) {
        return requestService.getAllRequestsByUserId(userId);
    }

    @GetMapping("/all")
    public Collection<ItemRequestResponseDto> getAllRequestsByOthers(@RequestHeader(userIdFromHeader) Long userId) {
        return requestService.getAllRequestsByOthers(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestResponseDto getOneRequestByUserId(@RequestHeader(userIdFromHeader) Long userId,
                                                        @PathVariable Long requestId) {
        return requestService.getOneRequestByUserId(userId, requestId);
    }

    @PostMapping
    public ItemRequestResponseDto createNewRequest(@RequestHeader(userIdFromHeader) Long userId,
                                                   @RequestBody ItemRequestCreateDto createDto) {
        return requestService.createNewRequest(userId, createDto);
    }

}
