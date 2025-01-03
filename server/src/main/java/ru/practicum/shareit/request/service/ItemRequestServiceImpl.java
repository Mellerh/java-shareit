package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.itemDtos.ItemMapper;
import ru.practicum.shareit.item.dto.itemDtos.ItemResponseDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.request.dto.ItemRequestResponseDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public List<ItemRequestResponseDto> getAllRequestsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Пользователь с id " + userId + " не найден."));

        List<ItemRequest> requestList = requestRepository.findAllByRequestorId(userId);


        List<Long> requestIds = requestList.stream().map(req -> req.getId()).toList();
        List<Item> itemList = itemRepository.findByRequest_IdIn(requestIds);


        if (itemList != null) {
            return upgradeItemRequest(itemList, requestList);
        } else {
            return requestList.stream().map((req -> ItemRequestMapper.toItemRequestDto(req))).toList();
        }
    }

    @Override
    public List<ItemRequestResponseDto> getAllRequestsByOthers(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Пользователь с id " + userId + " не найден."));

        List<ItemRequest> requestList = requestRepository.findAll(userId);

        List<Long> requestIds = requestList.stream().map(req -> req.getId()).toList();
        List<Item> itemList = itemRepository.findByRequest_IdIn(requestIds);


        if (itemList != null) {
            return upgradeItemRequest(itemList, requestList);
        } else {
            return requestList.stream().map((req -> ItemRequestMapper.toItemRequestDto(req))).toList();
        }

    }

    @Override
    public ItemRequestResponseDto getOneRequestByUserId(Long userId, Long requestId) {

        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Пользователь с id " + userId + " не найден."));

        ItemRequest request = requestRepository.findById(requestId).orElseThrow(()
                -> new NotFoundException("Запрос с id " + requestId + " не найден."));

        List<Item> itemList = itemRepository.findByRequest_Id(requestId);

        if (itemList != null) {
            List<ItemResponseDto> itemDtos = itemList.stream().map(item -> ItemMapper.toShortItemDto(item)).toList();
            return ItemRequestMapper.toItemRequestDto(request, itemDtos);
        } else {
            return ItemRequestMapper.toItemRequestDto(request);
        }

    }

    @Override
    public ItemRequestResponseDto createNewRequest(Long userId, ItemRequestCreateDto createDto) {

        User user = userRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Пользователь с id " + userId + " не найден."));

        ItemRequest request = ItemRequestMapper.toItemRequestModel(createDto, user);

        return ItemRequestMapper.toItemRequestDto(requestRepository.save(request));
    }


    private List<ItemRequestResponseDto> upgradeItemRequest(List<Item> itemList, List<ItemRequest> requestList) {
        Map<Long, List<Item>> itemListMap = itemList.stream()
                .collect(Collectors.groupingBy(item -> item.getRequest().getId()));

        List<ItemRequestResponseDto> dtoList = new ArrayList<>();
        for (ItemRequest request : requestList) {
            List<ItemResponseDto> itemsDtos = new ArrayList<>();

            List<Item> items = itemListMap.getOrDefault(request.getId(), Collections.emptyList());
            if (items != null) {
                itemsDtos = items.stream().map(item -> ItemMapper.toShortItemDto(item)).toList();
            }

            dtoList.add(ItemRequestMapper.toItemRequestDto(request, itemsDtos));
        }

        return dtoList;
    }
}
