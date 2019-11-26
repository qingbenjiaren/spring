package com.melo.spring.bean.factory.registry;

import com.melo.spring.bean.definition.BeanDefinition;

public interface BeanDefinitionRegistry {
    BeanDefinition getBeanDefinition(String name);

    void registerBeanDefinition(String name,BeanDefinition beanDefinition);
}
