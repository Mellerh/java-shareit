package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
//@Builder
public class Item {

    private Long ownerId;
    private Long id;
    private String name;
    private String description;
    private Boolean availableStatus;
    private String request;


}
