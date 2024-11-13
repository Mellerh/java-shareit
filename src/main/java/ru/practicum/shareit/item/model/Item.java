package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
@Builder
public class Item {

    private Long id;
    private Long ownerId;
    private String name;
    private String description;
    private Boolean availableStatus;
    private String request;


}
