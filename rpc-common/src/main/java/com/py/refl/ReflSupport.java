package com.py.refl;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

public class ReflSupport {

    public static Class<?> strToClazz(String classStr) {
        try {
            return Class.forName(classStr);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    public static Class<?>[] cusTypeToJavaType(String[] cusTypes) {
        Class<?>[] javaTypes = new Class[cusTypes.length];
        for (int i = 0; i < cusTypes.length; i++) {
            switch (cusTypes[i]) {
                case "string":
                    javaTypes[i] = String.class;
                    break;
                case "int":
                    javaTypes[i] = Integer.class;
                    break;
                default:
                    throw new RuntimeException();
            }
        }
        return javaTypes;
    }

    public static Object clazzToObj(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            System.err.println("实例化错误");
        } catch (IllegalAccessException e) {
            System.err.println("没有访问权限");
        }
        throw new RuntimeException("请检查clazz对象，clazz：" + clazz);
    }

    public static Method analyzeAndObtain(Class<?> clazz, String methodName, Class<?>[] types) {
        try {
            return clazz.getMethod(methodName, types);
        } catch (NoSuchMethodException e) {
            System.err.println("获取方法失败");
        }
        throw new RuntimeException("请检查clazz：" + clazz + "；methodName：" + methodName + "；types：" + types);
    }

    public static Object[] analyzeParams(Method method, Map<String, Object> params) {
        Parameter[] parameters = method.getParameters();
        Object[] paramsObj = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            String name = parameter.getName();
            Object value = params.get(name);
            paramsObj[i] = value;
        }
        return paramsObj;
    }

    public static String[] typeTransform(Parameter[] parameters) {
        String[] types = new String[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Class<?> type = parameters[i].getType();
            switch (type.getName()) {
                case "java.lang.String":
                    types[i] = "string";
                    break;
                case "java.lang.Integer":
                    types[i] = "int";
            }
        }
        return types;
    }

}
