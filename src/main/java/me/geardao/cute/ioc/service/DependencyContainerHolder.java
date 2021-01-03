package me.geardao.cute.ioc.service;

import me.geardao.cute.ioc.model.ComponentHolder;

import java.util.List;

public interface DependencyContainerHolder {

    Object getComponent(Class<?> componentType);

    Object getComponent(Class<?> componentType, String componentName);

    ComponentHolder getComponentHolder(Class<?> componentType);

    ComponentHolder getComponentHolder(Class<?> componentType, String componentName);

    List<ComponentHolder> getAllComponentHolder();

    void registerComponentHolder(ComponentHolder componentHolder);
}
