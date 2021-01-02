package me.geardao.cute.ioc.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

public class ComponentHolder {

    private Class<?> componentType;

    private Object instance;

    private Method postConstructorMethod;

    private Constructor<?> constructor;

    private Method[] beanMethods;

    private List<ComponentHolder> autoWiredComponents;

    private List<Class<?>> autoWiredClasses;

    public ComponentHolder(Class<?> componentType, Object instance, Method postConstructorMethod,
                           Constructor<?> constructor, Method[] beanMethods, List<ComponentHolder> autoWiredComponents, List<Class<?>> autoWiredClasses) {
        this.componentType = componentType;
        this.instance = instance;
        this.postConstructorMethod = postConstructorMethod;
        this.constructor = constructor;
        this.beanMethods = beanMethods;
        this.autoWiredComponents = autoWiredComponents;
        this.autoWiredClasses = autoWiredClasses;
    }

    public Class<?> getComponentType() {
        return componentType;
    }

    public void setComponentType(Class<?> componentType) {
        this.componentType = componentType;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public Method getPostConstructorMethod() {
        return postConstructorMethod;
    }

    public void setPostConstructorMethod(Method postConstructorMethod) {
        this.postConstructorMethod = postConstructorMethod;
    }

    public Constructor<?> getConstructor() {
        return constructor;
    }

    public void setConstructor(Constructor<?> constructor) {
        this.constructor = constructor;
    }

    public Method[] getBeanMethods() {
        return beanMethods;
    }

    public void setBeanMethods(Method[] beanMethods) {
        this.beanMethods = beanMethods;
    }

    public List<ComponentHolder> getAutoWiredComponents() {
        return autoWiredComponents;
    }

    public void setAutoWiredComponents(List<ComponentHolder> autoWiredComponents) {
        this.autoWiredComponents = autoWiredComponents;
    }
}
