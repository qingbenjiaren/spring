package com.melo.spring.bean.factory.registry;

import com.melo.spring.bean.definition.BeanDefinition;

import java.util.List;

public interface BeanDefinitionRegistry {
    BeanDefinition getBeanDefinition(String name);

    List<BeanDefinition> getBeanDefinitions();

    void registerBeanDefinition(String name,BeanDefinition beanDefinition);
}
