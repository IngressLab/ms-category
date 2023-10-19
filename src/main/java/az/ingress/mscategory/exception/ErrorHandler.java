package az.ingress.mscategory.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception e) {
        log.error("ActionLog.handle.{}", e.getClass().getName(), e);
        return new ErrorResponse(ExceptionMessages.UNEXPECTED_ERROR.getMessage());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(CONFLICT)
    public ErrorResponse handle(AlreadyExistsException e) {
        log.error("ActionLog.handle.{}", e.getClass().getName(), e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(METHOD_NOT_ALLOWED)
    public ErrorResponse handle(HttpRequestMethodNotSupportedException e) {
        log.error("ActionLog.handle.{}", e.getClass().getName(), e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(NotExistsException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handle(NotExistsException e) {
        log.error("ActionLog.handle.{}", e.getClass().getName(), e);
        return new ErrorResponse(e.getMessage());
    }
}