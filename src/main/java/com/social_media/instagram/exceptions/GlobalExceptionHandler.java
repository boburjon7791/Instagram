package com.social_media.instagram.exceptions;

import com.social_media.instagram.model.common.Header;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.FileNotFoundException;
import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Header<?> handleException(Exception e) {
        return Header.error(e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Header<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Header.badRequest(e.getMessage());
    }
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Header<?> handleBadRequestException(BadRequestException e) {
        return Header.badRequest(e.getMessage());
    }
    @ExceptionHandler({NotFoundException.class, NoResourceFoundException.class, FileNotFoundException.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public Header<?> handleNotFoundException(Exception e) {
        return Header.notFound(e.getMessage());
    }
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public Header<?> handleForbiddenException(ForbiddenException e) {
        return Header.forbidden(e.getMessage());
    }
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Header<?> handleUnauthorizedException(UnauthorizedException e) {
        return Header.unauthorized(e.getMessage());
    }
}
