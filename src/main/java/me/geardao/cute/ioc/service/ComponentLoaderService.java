package me.geardao.cute.ioc.service;

import me.geardao.cute.ioc.exception.ComponentLoaderException;
import me.geardao.cute.ioc.model.ComponentHolder;

import java.util.List;
import java.util.Set;

public interface ComponentLoaderService {
    List<ComponentHolder> load(Set<Class<?>> classes) throws ComponentLoaderException;
}
