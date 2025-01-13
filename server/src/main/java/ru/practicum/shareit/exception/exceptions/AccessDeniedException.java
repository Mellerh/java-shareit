package ru.practicum.shareit.exception.exceptions;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String massage) {
        super(massage);
    }

}
