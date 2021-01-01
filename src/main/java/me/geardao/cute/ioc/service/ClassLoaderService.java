package me.geardao.cute.ioc.service;

import me.geardao.cute.ioc.exception.ClassLoaderException;

import java.util.Set;

public interface ClassLoaderService {
    Set<Class<?>> load(Class<?> clazz) throws ClassLoaderException;
}
