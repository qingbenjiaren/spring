package com.melo.spring.bean.factory.support;

import com.melo.spring.bean.definition.BeanDefinition;
import com.melo.spring.bean.factory.AutowireCapableBeanFactory;
import com.melo.spring.bean.factory.BeanFactory;
import com.melo.spring.bean.factory.registry.support.DefaultSingletonBeanRegistry;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements AutowireCapableBeanFactory {
    //定义bean的获取流程，不关心实现
    public Object getBean(String name) {
        //从缓存中获取要找的对象
        Object singleton = getSingleton(name);
        if(singleton != null){
            return singleton;
        }
        //找不到指定名称的BeanDefinition对象
        //此处使用到的就是抽象模板方法，只定流程，不去实现
        BeanDefinition bd = getBeanDefinition(name);
        if(bd.isSingleton()){
            //根据BeanDefinition对象，完成bean的创建
            singleton = createBean(bd);
            addSingleton(name,singleton);
        }else{
            singleton = createBean(bd);
        }
        return singleton;
    }
    protected abstract BeanDefinition getBeanDefinition(String name);
}
