package me.geardao.cute.ioc.exception;

public class ClassLoaderException extends RuntimeException {

    public ClassLoaderException(String message) {
        super(message);
    }

    public ClassLoaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
