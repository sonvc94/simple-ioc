package me.geardao.cute.ioc.service.impl;

import me.geardao.cute.ioc.exception.InstanceInitializeException;
import me.geardao.cute.ioc.exception.PostConstructorExecutionException;
import me.geardao.cute.ioc.model.BeanHolder;
import me.geardao.cute.ioc.model.ComponentHolder;
import me.geardao.cute.ioc.service.InstanceInitializeService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InstanceInitializeServiceImpl implements InstanceInitializeService {

    @Override
    public void createInstance(ComponentHolder componentHolder) throws InstanceInitializeException {
        try {
            if (!(componentHolder instanceof BeanHolder)) {
                Object instance = componentHolder.getConstructor().newInstance(componentHolder.getConstructorDependenciesInstance());
                componentHolder.setInstance(instance);
                invokePostConstructor(componentHolder);
            } else {
                BeanHolder beanHolder = (BeanHolder) componentHolder;
                Method rootMethod = beanHolder.getRootMethod();
                rootMethod.setAccessible(true);
                Object newInstance = rootMethod.invoke(beanHolder.getInstance(), beanHolder.getConstructorDependenciesInstance());
                beanHolder.setInstance(newInstance);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new InstanceInitializeException(e.getMessage(), e);
        }
    }

    private void invokePostConstructor(ComponentHolder componentHolder) {
        Method postConstructorMethod = componentHolder.getPostConstructorMethod();
        if (postConstructorMethod == null) {
            return;
        }
        try {
            postConstructorMethod.setAccessible(true);
            postConstructorMethod.invoke(componentHolder.getInstance());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new PostConstructorExecutionException(e.getMessage(), e);
        }
    }

}
