package me.geardao.cute.ioc.service.impl;

import me.geardao.cute.ioc.annotation.Autowired;
import me.geardao.cute.ioc.annotation.Bean;
import me.geardao.cute.ioc.annotation.Component;
import me.geardao.cute.ioc.annotation.PostConstructor;
import me.geardao.cute.ioc.exception.ComponentLoaderException;
import me.geardao.cute.ioc.model.ComponentHolder;
import me.geardao.cute.ioc.service.ComponentLoaderService;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ComponentLoaderServiceImpl implements ComponentLoaderService {

    private final Set<ComponentHolder> componentHolders = new HashSet<>();

    public Set<ComponentHolder> load(Set<Class<?>> classes) throws ComponentLoaderException {
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
        List<Field> autoWiredFields = findAutoWiredFields(clazz);
        Class<?>[] autoWiredDependencies = findAutoWiredDependencies(autoWiredFields);
        Class<?>[] constructorDependencies = targetConstructor == null ? new Class[0] : targetConstructor.getParameterTypes();
        String componentName = findComponentName(clazz);
        ComponentHolder componentHolder = new ComponentHolder(
                componentName,
                clazz,
                null,
                postConstructorMethod,
                targetConstructor,
                beanMethods,
                autoWiredFields,
                constructorDependencies,
                new Object[constructorDependencies.length],
                autoWiredDependencies,
                new Object[autoWiredDependencies.length]
        );
        componentHolders.add(componentHolder);
    }

    private String findComponentName(Class<?> clazz) {
        Component annotation = clazz.getAnnotation(Component.class);
        if (annotation == null) {
            throw new ComponentLoaderException("missing component annotation on component class");
        }
        if (!annotation.value().equals("")) {
            return annotation.value();
        }
        String name = clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1);
        return name;
    }

    private Class<?>[] findAutoWiredDependencies(List<Field> autoWiredFields) {
        return autoWiredFields.stream().map(Field::getType).toArray(Class[]::new);
    }


    private List<Field> findAutoWiredFields(Class<?> clazz) {
        List<Field> autoWiredFields = new LinkedList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                autoWiredFields.add(field);
            }
        }
        return autoWiredFields;
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
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                return constructor;
            }
        }
        return constructors[0];
    }
}
