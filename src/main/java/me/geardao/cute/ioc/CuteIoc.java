package me.geardao.cute.ioc;

import me.geardao.cute.ioc.service.ClassLoaderService;
import me.geardao.cute.ioc.service.ClassLoaderServiceImpl;

import java.util.Set;

public class CuteIoc {

    public static void main(String[] args) {
        run(CuteIoc.class);
    }

    public static void run(Class<?> startUpClass) {
        ClassLoaderService classLoaderService = new ClassLoaderServiceImpl();
        Set<Class<?>> loadedClass = classLoaderService.load(startUpClass);
    }
}
