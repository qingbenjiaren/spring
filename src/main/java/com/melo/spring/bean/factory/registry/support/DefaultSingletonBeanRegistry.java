package com.melo.spring.bean.factory.registry.support;

import com.melo.spring.bean.factory.registry.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private Map<String,Object> singletons = new HashMap<>();
    @Override
    public Object getSingleton(String name) {
        return singletons.get(name);
    }

    @Override
    public void addSingleton(String name, Object obj) {
        this.singletons.put(name,obj);
    }
}
