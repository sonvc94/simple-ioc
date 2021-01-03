package me.geardao.cute.ioc.exception;

import me.geardao.cute.ioc.service.InstanceInitializeService;

public class PostConstructorExecutionException extends InstanceInitializeException {

    public PostConstructorExecutionException(String message) {
        super(message);
    }

    public PostConstructorExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
