package me.geardao.cute.ioc.exception;

public class ComponentLoaderException extends RuntimeException {

    public ComponentLoaderException(String message) {
        super(message);
    }

    public ComponentLoaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
