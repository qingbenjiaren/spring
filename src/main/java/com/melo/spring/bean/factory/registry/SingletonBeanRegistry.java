package com.melo.spring.bean.factory.registry;

public interface SingletonBeanRegistry {
    Object getSingleton(String name);

    void addSingleton(String name,Object obj);
}
