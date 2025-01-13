package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.client.ItemRequestClient;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;


@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Validated
public class ItemRequestController {

    private final String userIdFromHeader = "X-Sharer-User-Id";
    private final ItemRequestClient requestService;

    @GetMapping
    public ResponseEntity<Object> getAllRequestsByUserId(@RequestHeader(userIdFromHeader) @NotNull Long userId) {
        return requestService.getAllRequestsByUserId(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequestsByOthers(@RequestHeader(userIdFromHeader) @NotNull Long userId) {
        return requestService.getAllRequestsByOthers(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getOneRequestByUserId(@RequestHeader(userIdFromHeader) @NotNull Long userId,
                                                        @PathVariable Long requestId) {
        return requestService.getOneRequestByUserId(userId, requestId);
    }

    @PostMapping
    public ResponseEntity<Object> createNewRequest(@RequestHeader(userIdFromHeader) @NotNull Long userId,
                                                   @Valid @RequestBody ItemRequestCreateDto createDto) {
        return requestService.createNewRequest(userId, createDto);
    }

}
