package com.melo.spring.bean.factory.support;

import com.melo.spring.bean.aware.Aware;
import com.melo.spring.bean.aware.BeanFactoryAware;
import com.melo.spring.bean.definition.BeanDefinition;
import com.melo.spring.bean.definition.PropertyValue;
import com.melo.spring.bean.definition.RuntimeBeanReference;
import com.melo.spring.bean.definition.TypedStringValue;
import com.melo.spring.bean.strategy.ConvertValueStrategy;
import com.melo.spring.bean.strategy.surppot.StrategyBuilder;
import com.melo.spring.bean.utils.ReflectUtils;

import java.util.List;

public abstract class AbstractAutowireBeanFactory extends AbstractBeanFactory {
    public Object createBean(BeanDefinition beanDefinition){
        //完成bean的创建
        Class<?> clazz = resolveClassName(beanDefinition.getClazzName());
        if(clazz == null){
            return null;
        }
        //bean的创建分三个步骤
        //STEP1:实例化bean 此时只是创建了一个空的bean
        Object singleton = createBeanInstance(clazz);
        //bean属性填充
        populateBean(singleton,beanDefinition);

        //初始化bean
        initBean(singleton,beanDefinition);
        return singleton;
    }

    private void initBean(Object singleton, BeanDefinition beanDefinition) {
        //TODO 完成Aware接口（标记接口）相关的处理，spring mvc代码会用到
        if(singleton instanceof Aware){
            if(singleton instanceof BeanFactoryAware){
                ((BeanFactoryAware)singleton).setBeanFactory(this);
            }
        }
        //TODO BeanProcessor的前置方法执行
        initMethod(singleton,beanDefinition);
    }

    private void initMethod(Object singleton, BeanDefinition beanDefinition) {
        //TODO 完成InitializingBean（接口）的处理，调用的是afterPropertySet方法
        //完成init-method标签属性对应的方法调用
        ReflectUtils.invokeMethod(singleton,beanDefinition.getInitMethod());
    }

    private void populateBean(Object singleton, BeanDefinition beanDefinition) {
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        for(PropertyValue propertyValue : propertyValues){
            //获取属性名称
            String name = propertyValue.getName();
            //未处理的value
            Object value = propertyValue.getValue();
            //解决之后的value
            Object valueToUse = null;
            if(value instanceof TypedStringValue){
                //强转
                TypedStringValue typedStringValue = (TypedStringValue) value;
                String stringValue = typedStringValue.getValue();
                //参数类型
                Class<?> targetType = typedStringValue.getTargetType();
                //策略模式+简单工厂模式
                ConvertValueStrategy strategy = StrategyBuilder.buildStrategy(targetType);
                if(strategy == null){
                    continue;
                }
                valueToUse = strategy.convertValue(stringValue);
            }else if(value instanceof RuntimeBeanReference){
                //强转
                RuntimeBeanReference runtimeBeanReference = (RuntimeBeanReference) value;
                // 递归获取指定名称的bean实例
                // TODO 此处可能会发送循环依赖问题
                valueToUse = getBean(runtimeBeanReference.getRef());
            }else{
                //
            }
            //利用反射设置bean属性
            ReflectUtils.setProperty(singleton,name,valueToUse);
        }
    }

    private Object createBeanInstance(Class<?> clazz){
        // TODO 可以根据bean标签的配置选择使用实例工厂去创建Bean
        // TODO 可以根据bean标签的配置选择使用静态工厂去创建Bean

        // 还可以选择使用我们的构造方法去创建Bean
        return ReflectUtils.createObject(clazz);
    }

    private Class<?> resolveClassName(String clazzName) {
        try {
            return Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
