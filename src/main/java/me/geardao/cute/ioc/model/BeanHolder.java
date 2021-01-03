package me.geardao.cute.ioc.model;


import me.geardao.cute.ioc.annotation.Bean;

import java.lang.reflect.Method;

public class BeanHolder extends ComponentHolder {

    private final Method originMethod;

    private final ComponentHolder rootComponent;

    public BeanHolder(Class<?> beanType, Method rootMethod, ComponentHolder rootComponent) {
        super();
        this.setComponentName(findBeanName(rootMethod));
        this.setInstance(rootComponent.getInstance());
        this.setComponentType(beanType);
        this.originMethod = rootMethod;
        this.rootComponent = rootComponent;
        this.setConstructorDependencies(rootMethod.getParameterTypes());
        this.setConstructorDependenciesInstance(new Object[rootMethod.getParameterTypes().length]);
    }

    private String findBeanName(Method rootMethod) {
        if (rootMethod.isAnnotationPresent(Bean.class)) {
            Bean bean = rootMethod.getAnnotation(Bean.class);
            String value = bean.value();
            if (value.equals("")) {
                return rootMethod.getName();
            } else {
                return bean.value();
            }
        }
        return rootMethod.getName();
    }

    public Method getRootMethod() {
        return originMethod;
    }

    public ComponentHolder getRootComponent() {
        return rootComponent;
    }

}
