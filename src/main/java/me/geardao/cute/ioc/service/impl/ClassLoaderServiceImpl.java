package me.geardao.cute.ioc.service.impl;

import me.geardao.cute.ioc.exception.ClassLoaderException;
import me.geardao.cute.ioc.service.ClassLoaderService;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ClassLoaderServiceImpl implements ClassLoaderService {

    private final Set<Class<?>> loadedClass = new HashSet<>();

    public Set<Class<?>> load(Class<?> clazz) throws ClassLoaderException {
        String location = clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
        File file = new File(location);
        for (File f : file.listFiles()) {
            try {
                scan(f, "");
            } catch (ClassNotFoundException e) {
                throw new ClassLoaderException(e.getMessage(), e);
            }
        }
        return loadedClass;
    }

    public void scan(File file, String packageName) throws ClassNotFoundException {
        if (file.isDirectory()) {
            packageName = packageName + file.getName() + ".";
            for (File f : file.listFiles()) {
                scan(f, packageName);
            }
        }
        // current file is not a folder
        String name = packageName + file.getName();
        if (!name.endsWith(".class")) {
            return;
        }
        name = name.replace(".class", "");
        loadedClass.add(Class.forName(name));
    }
}
