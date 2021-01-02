package me.geardao.cute.ioc;

import me.geardao.cute.ioc.model.ComponentHolder;
import me.geardao.cute.ioc.service.ClassLoaderService;
import me.geardao.cute.ioc.service.ClassLoaderServiceImpl;
import me.geardao.cute.ioc.service.ComponentLoaderService;
import me.geardao.cute.ioc.service.ComponentLoaderServiceImpl;

import java.util.List;
import java.util.Set;

public class CuteIoc {

    public static void main(String[] args) {
        run(CuteIoc.class);
    }

    public static void run(Class<?> startUpClass) {
        ClassLoaderService classLoaderService = new ClassLoaderServiceImpl();
        Set<Class<?>> loadedClass = classLoaderService.load(startUpClass);
        ComponentLoaderService componentLoaderService = new ComponentLoaderServiceImpl();
        List<ComponentHolder> loadedServices = componentLoaderService.load(loadedClass);
        System.out.println(loadedServices.size());
    }
}
