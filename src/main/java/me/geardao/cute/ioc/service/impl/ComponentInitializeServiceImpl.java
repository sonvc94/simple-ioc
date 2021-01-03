package me.geardao.cute.ioc.service.impl;

import me.geardao.cute.ioc.exception.ComponentInitializeException;
import me.geardao.cute.ioc.exception.InstanceInitializeException;
import me.geardao.cute.ioc.model.BeanHolder;
import me.geardao.cute.ioc.model.ComponentHolder;
import me.geardao.cute.ioc.service.ComponentInitializeService;
import me.geardao.cute.ioc.service.DependencyContainerHolder;
import me.geardao.cute.ioc.service.InstanceInitializeService;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Set;

public class ComponentInitializeServiceImpl implements ComponentInitializeService {

    private final InstanceInitializeService instanceInitializeService;

    private final LinkedList<ComponentHolder> componentHolderQueue = new LinkedList<>();

    private final DependencyContainerHolder dependencyContainerHolder;

    public ComponentInitializeServiceImpl(InstanceInitializeService instanceInitializeService, DependencyContainerHolder dependencyContainerHolder) {
        this.instanceInitializeService = instanceInitializeService;
        this.dependencyContainerHolder = dependencyContainerHolder;
    }

    @Override
    public DependencyContainerHolder initializeComponentAndBean(Set<ComponentHolder> componentHolders)
            throws ComponentInitializeException {
        init(componentHolders);
        int counter = 0;
        while (!componentHolderQueue.isEmpty()) {
            if (counter >= 1_000_000) {
                throw new ComponentInitializeException("reached limit object init iterator time");
            }
            ComponentHolder enqueueComponentHolder = componentHolderQueue.removeFirst();
            fillDependencies(enqueueComponentHolder);
            if (enqueueComponentHolder.isResolve()) {
                this.instanceInitializeService.createInstance(enqueueComponentHolder);
                this.registerBean(enqueueComponentHolder);
                this.dependencyContainerHolder.registerComponentHolder(enqueueComponentHolder);
            } else {
                counter++;
                componentHolderQueue.addLast(enqueueComponentHolder);
            }
        }
        for (ComponentHolder componentHolder : dependencyContainerHolder.getAllComponentHolder()) {
            for (int i = 0; i < componentHolder.getAutoWiredFields().size(); i++) {
                Field field = componentHolder.getAutoWiredFields().get(i);
                field.setAccessible(true);
                try {
                    field.set(componentHolder.getInstance(), componentHolder.getAutoWiredDependenciesInstance()[i]);
                } catch (IllegalAccessException e) {
                    throw new InstanceInitializeException(e.getMessage(), e);
                }
            }
        }
        return dependencyContainerHolder;
    }

    private void registerBean(ComponentHolder componentHolder) {
        for (Method beanMethod : componentHolder.getBeanMethods()) {
            BeanHolder beanHolder = new BeanHolder(beanMethod.getReturnType(), beanMethod, componentHolder);
            if (!beanHolder.isResolve()) {
                fillDependencies(beanHolder);
                componentHolderQueue.addLast(beanHolder);
                continue;
            }
            instanceInitializeService.createInstance(beanHolder);
            this.dependencyContainerHolder.registerComponentHolder(beanHolder);
        }
    }

    private void fillDependencies(ComponentHolder componentholder) {
        for (int i = 0; i < componentholder.getConstructorDependenciesInstance().length; i++) {
            Object o = componentholder.getConstructorDependenciesInstance()[i];
            if (o == null) {
                for (ComponentHolder availableComponent : dependencyContainerHolder.getAllComponentHolder()) {
                    if (availableComponent.getComponentType().isAssignableFrom(componentholder.getConstructorDependencies()[i])) {
                        componentholder.getConstructorDependenciesInstance()[i] = availableComponent.getInstance();
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < componentholder.getAutoWiredDependenciesInstance().length; i++) {
            Object o = componentholder.getAutoWiredDependenciesInstance()[i];
            if (o == null) {
                for (ComponentHolder availableComponent : dependencyContainerHolder.getAllComponentHolder()) {
                    if (availableComponent.getComponentType().isAssignableFrom(componentholder.getAutoWiredDependencies()[i])) {
                        componentholder.getAutoWiredDependenciesInstance()[i] = availableComponent.getInstance();
                    }
                }
            }
        }
    }

    private void init(Set<ComponentHolder> componentHolders) {
        componentHolderQueue.clear();
        componentHolderQueue.addAll(componentHolders);
    }

}

