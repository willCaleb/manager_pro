package com.project.pro.exception;

import com.project.pro.enums.EnumCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomRuntimeException extends RuntimeException {

    public CustomRuntimeException() {
        super("Ocorreu um erro");
    }

    @Deprecated
    public CustomRuntimeException(String message) {
        super(message);
    }

    @Deprecated
    public CustomRuntimeException(String message, Throwable cause) {
        this(MessageExceptionHandler.getMessage(message, cause));
    }

    public CustomRuntimeException(Throwable cause) {
        this(MessageExceptionHandler.getMessage(cause));
    }

    public CustomRuntimeException(String message, List<String> arguments) {
        this(MessageExceptionHandler.getMessage(message, arguments));
    }

    @Deprecated
    public CustomRuntimeException(String message, String argument) {
        this(MessageExceptionHandler.getMessage(message, argument));
    }

    public CustomRuntimeException(EnumCustomException customMessage, Object... arguments) {
        this(MessageExceptionHandler.getMessage(customMessage, arguments));
    }

    @Deprecated
    public CustomRuntimeException(String customMessage, Object... arguments) {
        this(MessageExceptionHandler.getMessage(customMessage, arguments));
    }
}
