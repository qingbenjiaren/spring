package com.melo.spring.bean.factory.support;

import com.melo.spring.bean.definition.BeanDefinition;
import com.melo.spring.bean.factory.registry.BeanDefinitionRegistry;
import com.melo.spring.bean.resource.ClasspathResource;
import com.melo.spring.bean.resource.Resource;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DefaultListableBeanFactory extends AbstractAutowireBeanFactory implements BeanDefinitionRegistry {

    /**
     *  以beanName为key，以BeanDefinition对象为value的存储集合
     */
    private Map<String,BeanDefinition> beanDefinitionMap = new HashMap<>();

    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(name,beanDefinition);
    }
}
