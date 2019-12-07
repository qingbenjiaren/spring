package com.melo.spring.bean.aware;

import com.melo.spring.bean.factory.BeanFactory;

/**
 * 谁实现该接口，就可以被注入一个beanFactory实例
 */
public interface BeanFactoryAware extends Aware {
    void setBeanFactory(BeanFactory beanFactory);
}
