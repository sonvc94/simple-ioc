package me.geardao.cute.ioc.service;

import me.geardao.cute.ioc.annotation.Autowired;
import me.geardao.cute.ioc.annotation.Bean;
import me.geardao.cute.ioc.annotation.Component;
import me.geardao.cute.ioc.annotation.PostConstructor;
import me.geardao.cute.ioc.exception.ComponentLoaderException;
import me.geardao.cute.ioc.model.ComponentHolder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ComponentLoaderServiceImpl implements ComponentLoaderService {

    private final List<ComponentHolder> componentHolders = new ArrayList<>();

    public List<ComponentHolder> load(Set<Class<?>> classes) throws ComponentLoaderException {
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Component.class)) {
                scan(clazz);
            }
        }
        return componentHolders;
    }

    private void scan(Class<?> clazz) {
        Method postConstructorMethod = findPostConstructorMethod(clazz);
        Constructor<?> targetConstructor = findTargetConstructor(clazz);
        Method[] beanMethods = findBeanMethods(clazz);
        List<Class<?>> autoWiredClasses = findAutoWiredClasses(clazz);
        ComponentHolder componentHolder = new ComponentHolder(
                clazz,
                null,
                postConstructorMethod,
                targetConstructor,
                beanMethods,
                null,
                autoWiredClasses
        );
        componentHolders.add(componentHolder);
    }

    private Method[] findBeanMethods(Class<?> clazz) {
        List<Method> beanMethods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Bean.class)) {
                beanMethods.add(method);
            }
        }
        return beanMethods.toArray(new Method[0]);
    }

    private Method findPostConstructorMethod(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.getReturnType().equals(void.class) && !method.getReturnType().equals(Void.class)) {
                continue;
            }
            if (method.isAnnotationPresent(PostConstructor.class)) {
                return method;
            }
        }
        return null;
    }

    private Constructor<?> findTargetConstructor(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                return constructor;
            }
        }
        return null;
    }

    private List<Class<?>> findAutoWiredClasses(Class<?> clazz) {
        List<Class<?>> autoWiredClasses = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Class<?> type = field.getType();
                autoWiredClasses.add(type);
            }
        }
        return autoWiredClasses;
    }
}
