package me.geardao.cute.ioc.service.impl;

import me.geardao.cute.ioc.model.ComponentHolder;
import me.geardao.cute.ioc.model.DependencyContainer;
import me.geardao.cute.ioc.service.DependencyContainerHolder;

import java.util.List;

public class DependencyContainerHolderImpl implements DependencyContainerHolder {

    DependencyContainer dependencyContainer;

    public DependencyContainerHolderImpl() {
        dependencyContainer = new DependencyContainer();
    }

    @Override
    public Object getComponent(Class<?> componentType) {
        for (ComponentHolder componentHolder : dependencyContainer.getComponentHolders()) {
            if (componentHolder.getComponentType().isAssignableFrom(componentType)) {
                return componentHolder.getInstance();
            }
        }
        return null;
    }

    @Override
    public Object getComponent(Class<?> componentType, String componentName) {
        return null;
    }

    @Override
    public ComponentHolder getComponentHolder(Class<?> componentType) {
        for (ComponentHolder componentHolder : this.dependencyContainer.getComponentHolders()) {
            if (componentHolder.getComponentType().isAssignableFrom(componentType)) {
                return componentHolder;
            }
        }
        return null;
    }

    @Override
    public ComponentHolder getComponentHolder(Class<?> componentType, String componentName) {
        return null;
    }

    @Override
    public List<ComponentHolder> getAllComponentHolder() {
        return dependencyContainer.getComponentHolders();
    }

    @Override
    public void registerComponentHolder(ComponentHolder componentHolder) {
        dependencyContainer.registerComponentHolder(componentHolder);
    }
}
