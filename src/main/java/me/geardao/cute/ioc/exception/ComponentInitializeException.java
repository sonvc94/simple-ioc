package me.geardao.cute.ioc.exception;

public class ComponentInitializeException extends RuntimeException {

    public ComponentInitializeException(String message) {
        super(message);
    }

    public ComponentInitializeException(String message, Throwable cause) {
        super(message, cause);
    }
}
