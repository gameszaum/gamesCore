package com.gameszaum.core.api.classes;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassGetter {

    private ClassGetter() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static List<Class<?>> getClassesForPackage(JavaPlugin plugin, String pkgname) {
        List<Class<?>> classes = new ArrayList<>();

        CodeSource src = plugin.getClass().getProtectionDomain().getCodeSource();
        if (src != null) {
            URL resource = src.getLocation();
            processJarfile(resource, pkgname, classes);
        }
        return classes;
    }

    public static List<Class<?>> getClassesForPackage(Plugin plugin, String pkgname) {
        List<Class<?>> classes = new ArrayList<Class<?>>();

        CodeSource src = plugin.getClass().getProtectionDomain().getCodeSource();
        if (src != null) {
            URL resource = src.getLocation();
            processJarfile(resource, pkgname, classes);
        }
        return classes;
    }

    public static List<Class<?>> getClassesForPackageSimple(JavaPlugin plugin, String pkgname) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        CodeSource src = plugin.getClass().getProtectionDomain().getCodeSource();
        if (src != null) {
            URL resource = src.getLocation();
            processJarfile(resource, pkgname, classes);
        }
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Class<?>> classi = new ArrayList<Class<?>>();
        for (Class<?> classy : classes) {
            names.add(classy.getSimpleName());
            classi.add(classy);
        }
        classes.clear();
        names.sort(String.CASE_INSENSITIVE_ORDER);
        for (String s : names)
            for (Class<?> classy : classi) {
                if (classy.getSimpleName().equals(s)) {
                    classes.add(classy);
                    break;
                }
            }
        return classes;
    }

    private static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unexpected ClassNotFoundException loading class " + className + "");
        }
    }

    private static void processJarfile(URL resource, String pkgname, List<Class<?>> classes) {
        String relPath = pkgname.replace('.', '/');
        String resPath = resource.getPath().replace("%20", " ");
        String jarPath = resPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
        JarFile jarFile;
        try {
            jarFile = new JarFile(jarPath);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException reading JAR File " + jarPath + "", e);
        }
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String entryName = entry.getName();
            String className = null;
            if (entryName.endsWith(".class") && entryName.startsWith(relPath) && entryName.length() > (relPath.length() + "/".length())) {
                className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
            }
            if (className != null) {
                classes.add(loadClass(className));
            }
        }
    }

}
