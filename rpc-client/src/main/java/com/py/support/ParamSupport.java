package com.py.support;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class ParamSupport {

    public static Map<String, Object> paramTransform(Parameter[] parameters,
                                                     Object[] args) {
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String argName = parameters[i].getName();
            params.put(argName, args[i]);
        }
        return params;
    }
}
