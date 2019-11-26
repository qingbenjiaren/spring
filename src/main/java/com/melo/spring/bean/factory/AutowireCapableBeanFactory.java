package com.melo.spring.bean.factory;

import com.melo.spring.bean.definition.BeanDefinition;

public interface AutowireCapableBeanFactory extends BeanFactory {
    void createBean(BeanDefinition beanDefinition);
}
