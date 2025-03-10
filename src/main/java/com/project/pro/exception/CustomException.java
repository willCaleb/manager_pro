package com.project.pro.exception;

import com.project.pro.enums.EnumCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomException extends RuntimeException {

    public CustomException() {
        super("Ocorreu um erro");
    }

    @Deprecated
    public CustomException(String message) {
        super(message);
    }

    @Deprecated
    public CustomException(String message, Throwable cause) {
        this(MessageExceptionHandler.getMessage(message, cause));
    }

    public CustomException(Throwable cause) {
        this(MessageExceptionHandler.getMessage(cause));
    }

    public CustomException(String message, List<String> arguments) {
        this(MessageExceptionHandler.getMessage(message, arguments));
    }

    @Deprecated
    public CustomException(String message, String argument) {
        this(MessageExceptionHandler.getMessage(message, argument));
    }

    public CustomException(EnumCustomException customMessage, Object... arguments) {
        this(MessageExceptionHandler.getMessage(customMessage, arguments));
    }

    @Deprecated
    public CustomException(String customMessage, Object... arguments) {
        this(MessageExceptionHandler.getMessage(customMessage, arguments));
    }
}
