package com.py.protocol;

import java.util.Map;

public class InfProtocol {

    /**
     * 接口名称
     */
    private String inf;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 方法参数类型
     * 约定类型-->java类型
     * string-->String
     * int-->Integer
     */
    private String[] types;

    /**
     * 方法参数
     * key：参数名称
     * value：参数值
     */
    private Map<String, Object> params;


    public String getInf() {
        return inf;
    }

    public void setInf(String inf) {
        this.inf = inf;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
