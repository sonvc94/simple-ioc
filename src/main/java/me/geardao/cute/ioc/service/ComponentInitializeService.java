package me.geardao.cute.ioc.service;

import me.geardao.cute.ioc.exception.ComponentInitializeException;
import me.geardao.cute.ioc.model.ComponentHolder;

import java.util.Set;

public interface ComponentInitializeService {

    DependencyContainerHolder initializeComponentAndBean(Set<ComponentHolder> componentHolders) throws ComponentInitializeException;

}
