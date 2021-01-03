package me.geardao.cute.ioc;

import me.geardao.cute.ioc.model.ComponentHolder;
import me.geardao.cute.ioc.service.*;
import me.geardao.cute.ioc.service.impl.*;

import java.util.Set;

public class CuteIoc {

    public static DependencyContainerHolder run(Class<?> startUpClass) {
        ClassLoaderService classLoaderService = new ClassLoaderServiceImpl();
        Set<Class<?>> loadedClass = classLoaderService.load(startUpClass);
        ComponentLoaderService componentLoaderService = new ComponentLoaderServiceImpl();
        Set<ComponentHolder> loadedServices = componentLoaderService.load(loadedClass);
        InstanceInitializeService instanceInitializeService = new InstanceInitializeServiceImpl();
        DependencyContainerHolder dependencyContainerHolder = new DependencyContainerHolderImpl();
        ComponentInitializeService componentInitializeService = new ComponentInitializeServiceImpl(
                instanceInitializeService, dependencyContainerHolder);
        return componentInitializeService.initializeComponentAndBean(loadedServices);
    }
}
