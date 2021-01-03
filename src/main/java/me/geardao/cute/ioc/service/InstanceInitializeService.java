package me.geardao.cute.ioc.service;

import me.geardao.cute.ioc.exception.InstanceInitializeException;
import me.geardao.cute.ioc.model.ComponentHolder;

public interface InstanceInitializeService {
    void createInstance(ComponentHolder componentHolder) throws InstanceInitializeException;
}
