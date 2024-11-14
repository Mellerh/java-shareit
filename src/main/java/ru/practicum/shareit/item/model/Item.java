package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.user.model.User;

@Data
@EqualsAndHashCode(of = "id")
@Builder
public class Item {

    private Long id;
    private User owner;
    private String name;
    private String description;
    private Boolean available;
    private String request;


}
