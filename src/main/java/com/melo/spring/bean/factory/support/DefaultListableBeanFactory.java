package com.melo.spring.bean.factory.support;

import com.melo.spring.bean.definition.BeanDefinition;
import com.melo.spring.bean.factory.ListableBeanFactory;
import com.melo.spring.bean.factory.registry.BeanDefinitionRegistry;
import com.melo.spring.bean.resource.ClasspathResource;
import com.melo.spring.bean.resource.Resource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultListableBeanFactory extends AbstractAutowireBeanFactory implements BeanDefinitionRegistry, ListableBeanFactory {

    /**
     *  以beanName为key，以BeanDefinition对象为value的存储集合
     */
    private Map<String,BeanDefinition> beanDefinitionMap = new HashMap<>();

    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public List<BeanDefinition> getBeanDefinitions() {
        return new ArrayList<>(beanDefinitionMap.values());
    }

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(name,beanDefinition);
    }

    @Override
    public<T> List<T> getBeansByType(Class<?> type) {
        List<T> beans = new ArrayList<>();
        try{
            for(BeanDefinition bd : beanDefinitionMap.values()){
                String clazzName = bd.getClazzName();
                Class<?> clazz = Class.forName(clazzName);
                if(type.isAssignableFrom(clazz)){
                    Object bean = getBean(bd.getBeanName());
                    beans.add((T) bean);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return beans;
    }

    @Override
    public<T> List<T> getBeanNamesByType(Class<?> type) {
        return null;
    }
}
