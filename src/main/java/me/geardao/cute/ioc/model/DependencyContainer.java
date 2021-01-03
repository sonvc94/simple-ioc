package me.geardao.cute.ioc.model;

import me.geardao.cute.ioc.exception.RegisterComponentException;

import java.util.LinkedList;
import java.util.List;

public class DependencyContainer {

    private static final String DUPLICATE_COMPONENT_NAME = "two components have same name %s.";

    private final List<ComponentHolder> componentHolders;

    public DependencyContainer() {
        this.componentHolders = new LinkedList<>();
    }

    public List<ComponentHolder> getComponentHolders() {
        return componentHolders;
    }

    public void registerComponentHolder(ComponentHolder registerComponentHolder) {
        for (ComponentHolder holder : componentHolders) {
            if (holder.getComponentName().equalsIgnoreCase(registerComponentHolder.getComponentName())) {
                throw new RegisterComponentException(String.format(DUPLICATE_COMPONENT_NAME, holder.getComponentName()));
            }
        }
        this.componentHolders.add(registerComponentHolder);
    }
}
