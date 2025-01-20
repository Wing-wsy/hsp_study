package com.yz.mis.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author wing
 * @create 2025/1/20
 */
public class MyRequestWrapper extends HttpServletRequestWrapper{
    private Map params = new HashMap<>();

    public MyRequestWrapper(HttpServletRequest request, Map newParams) {
        super(request);
        if(request.getParameterMap() != null){
            this.params.putAll(request.getParameterMap());
        }
        if(newParams != null){
            this.params.putAll(newParams);
        }
    }

    //主要覆盖这个方法来获取新的参数对象
    @Override
    public Map getParameterMap() {
        return params;
    }

    @Override
    public String[] getParameterValues(String name) {
        Object v = params.get(name);
        if (v == null) {
            return null;
        } else if (v instanceof String[]) {
            return (String[]) v;
        } else if (v instanceof String) {
            return new String[]{(String) v};
        } else {
            return new String[]{v.toString()};
        }
    }

    /**
     * 根据参数的key获取参数
     * @param name
     * @return
     */
    @Override
    public String getParameter(String name) {
        Object v = params.get(name);
        if (v == null) {
            return null;
        } else if (v instanceof String[]) {
            String[] strArr = (String[]) v;
            if (strArr.length > 0) {
                return strArr[0];
            } else {
                return null;
            }
        } else if (v instanceof String) {
            return (String) v;
        } else {
            return v.toString();
        }
    }

    public Enumeration getParameterNames() {
        Vector l = new Vector(params.keySet());
        return l.elements();
    }
}
