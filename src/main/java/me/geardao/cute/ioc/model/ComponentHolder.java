package me.geardao.cute.ioc.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class ComponentHolder {

    private String componentName;

    private Class<?> componentType;

    private Object instance;

    private Method postConstructorMethod;

    private Constructor<?> constructor;

    private Method[] beanMethods;

    private List<Field> autoWiredFields;

    private Class<?>[] constructorDependencies;

    private Object[] constructorDependenciesInstance;

    private Class<?>[] autoWiredDependencies;

    private Object[] autoWiredDependenciesInstance;

    public ComponentHolder() {
        this.beanMethods = new Method[0];
        this.autoWiredFields = new LinkedList<>();
        this.constructorDependencies = new Class<?>[0];
        this.constructorDependenciesInstance = new Class<?>[0];
        this.autoWiredDependencies = new Class<?>[0];
        this.autoWiredDependenciesInstance = new Object[0];
    }

    public ComponentHolder(
            String componentName,
            Class<?> componentType,
            Object instance,
            Method postConstructorMethod,
            Constructor<?> targetConstructor,
            Method[] beanMethods,
            List<Field> autoWiredFields,
            Class<?>[] constructorDependencies,
            Object[] constructorDependenciesInstance,
            Class<?>[] autoWiredDependencies,
            Object[] autoWiredDependenciesInstance
    ) {
        this.componentName = componentName;
        this.componentType = componentType;
        this.instance = instance;
        this.postConstructorMethod = postConstructorMethod;
        this.constructor = targetConstructor;
        this.beanMethods = beanMethods;
        this.autoWiredFields = autoWiredFields;
        this.constructorDependencies = constructorDependencies;
        this.constructorDependenciesInstance = constructorDependenciesInstance;
        this.autoWiredDependencies = autoWiredDependencies;
        this.autoWiredDependenciesInstance = autoWiredDependenciesInstance;
    }

    public boolean isResolve() {
        for (Object dependencyInstance : constructorDependenciesInstance) {
            if (dependencyInstance == null) {
                return false;
            }
        }
        for (Object dependencyInstance : autoWiredDependenciesInstance) {
            if (dependencyInstance == null) {
                return false;
            }
        }
        return true;
    }

    public void registryDependency(Object instance) {
        for (int i = 0; i < constructorDependencies.length; i++) {
            if (constructorDependencies[i].isAssignableFrom(instance.getClass())) {
                constructorDependenciesInstance[i] = instance;
                return;
            }
        }
        for (int i = 0; i < autoWiredDependencies.length; i++) {
            if (autoWiredDependencies[i].isAssignableFrom(instance.getClass())) {
                autoWiredDependenciesInstance[i] = instance;
                return;
            }
        }
    }

    public boolean isDependencyRequired(Class<?> dependency) {
        for (Class<?> constructorDependency : constructorDependencies) {
            if (constructorDependency.isAssignableFrom(dependency)) {
                return true;
            }
        }
        for (Class<?> autoWiredDependency : autoWiredDependencies) {
            if (autoWiredDependency.isAssignableFrom(dependency)) {
                return true;
            }
        }
        return false;
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

    public List<Field> getAutoWiredFields() {
        return autoWiredFields;
    }

    protected void setAutoWiredFields(List<Field> autoWiredFields) {
        this.autoWiredFields = autoWiredFields;
    }

    public Object[] getConstructorDependenciesInstance() {
        return constructorDependenciesInstance;
    }

    protected void setConstructorDependenciesInstance(Object[] constructorDependenciesInstance) {
        this.constructorDependenciesInstance = constructorDependenciesInstance;
    }

    public Class<?>[] getConstructorDependencies() {
        return constructorDependencies;
    }

    protected void setConstructorDependencies(Class<?>[] constructorDependencies) {
        this.constructorDependencies = constructorDependencies;
    }

    public Object[] getAutoWiredDependenciesInstance() {
        return autoWiredDependenciesInstance;
    }

    protected void setAutoWiredDependenciesInstance(Object[] autoWiredDependenciesInstance) {
        this.autoWiredDependenciesInstance = autoWiredDependenciesInstance;
    }

    public Class<?>[] getAutoWiredDependencies() {
        return autoWiredDependencies;
    }

    protected void setAutoWiredDependencies(Class<?>[] autoWiredDependencies) {
        this.autoWiredDependencies = autoWiredDependencies;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }
}
