package me.geardao.cute.ioc.exception;

public class RegisterComponentException extends RuntimeException {

    public RegisterComponentException(String message) {
        super(message);
    }

    public RegisterComponentException(String message, Throwable cause) {
        super(message, cause);
    }
}
