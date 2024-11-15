package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exception.exceptions.DataConflictException;
import ru.practicum.shareit.exception.exceptions.DuplicatedDataException;
import ru.practicum.shareit.exception.exceptions.NotFoundException;


/**
 * общий обработчик ошибок
 */

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(final Exception e) {
        log.warn("Error", e);
        return new ErrorResponse("Error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.info("NotFoundException", e);
        return new ErrorResponse("NotFoundException", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicatedDataException(final DuplicatedDataException e) {
        log.info("DuplicatedDataException", e);
        return new ErrorResponse("DuplicatedDataException", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleDataConflictException(final DataConflictException e) {
        log.info("DataConflictException", e);
        return new ErrorResponse("DataConflictException", e.getMessage());
    }

}
