package me.geardao.cute.ioc.exception;

public class InstanceInitializeException extends RuntimeException {

    public InstanceInitializeException(String message) {
        super(message);
    }

    public InstanceInitializeException(String message, Throwable cause) {
        super(message, cause);
    }
}
