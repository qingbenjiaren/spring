package com.melo.spring.bean.xml;

import com.melo.spring.bean.definition.BeanDefinition;
import com.melo.spring.bean.definition.PropertyValue;
import com.melo.spring.bean.definition.RuntimeBeanReference;
import com.melo.spring.bean.definition.TypedStringValue;
import com.melo.spring.bean.factory.registry.BeanDefinitionRegistry;
import com.melo.spring.bean.utils.ReflectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import java.util.List;

public class XmlBeanDefinitionDocumentReader {
    public BeanDefinitionRegistry beanDefinitionRegistry;
    public XmlBeanDefinitionDocumentReader(BeanDefinitionRegistry beanDefinitionRegistry){
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }
    public void registerBeanDefinitions(Element rootElement){
        //获取<bean>和自定义标签
        List<Element> elements  = rootElement.elements();
        for(Element element : elements){
            //获取标签名
            String name = element.getName();
            if(name.equals("bean")){
                parseDefaultElement(element);
            }else{
                //解析自定义标签
                parseCustomElement(element);
            }
        }
    }

    private void parseCustomElement(Element element) {

    }

    private void parsePropertyElement(BeanDefinition beanDefinition, Element propertyElement) {
        if(propertyElement == null){
            return;
        }
        //获取name属性
        String name = propertyElement.attributeValue("name");
        String value = propertyElement.attributeValue("value");
        String ref = propertyElement.attributeValue("ref");
        //如果value和ref都有值，则返回
        if(StringUtils.isNotEmpty(value) && StringUtils.isNotEmpty(ref)){
            return;
        }
        PropertyValue pv = null;
        if(StringUtils.isNotEmpty(value)){
            //因为spring配置文件中value是String类型，而对象中的属性是各种各样的，所以需要存储类型
            TypedStringValue typedStringValue = new TypedStringValue(value);
            Class<?> targetType = ReflectUtils.getTypeByFieldName(beanDefinition.getClazzName(),name);
            typedStringValue.setTargetType(targetType);
            pv = new PropertyValue(name,typedStringValue);
            beanDefinition.addPropertyValue(pv);
        }else{
            RuntimeBeanReference reference = new RuntimeBeanReference(ref);
            pv = new PropertyValue(name,reference);
            beanDefinition.addPropertyValue(pv);
        }
    }

    private void parseDefaultElement(Element beanElement) {
        try {
            if(beanElement == null){
                return;
            }
            //获取ID
            String id = beanElement.attributeValue("id");
            //获取name
            String name = beanElement.attributeValue("name");
            //获取class
            String clazzName = beanElement.attributeValue("class");
            if(StringUtils.isEmpty(clazzName)){
                return;
            }
            Class<?> clazzType = Class.forName(clazzName);
            //获取init-method
            String initMethod = beanElement.attributeValue("init-method");
            //获取scope
            String scope = beanElement.attributeValue("scope");
            //scope默认为singleton
            scope = scope != null && !scope.equals("") ? scope : "singleton";
            String beanName = id == null ? name : id;
            beanName = beanName == null ? clazzType.getSimpleName() : beanName;
            //创建BeanDefinition对象
            BeanDefinition beanDefinition = new BeanDefinition(clazzName,beanName);
            beanDefinition.setInitMethod(initMethod);
            beanDefinition.setScope(scope);
            //获取property子集合
            List<Element> propertyElements = beanElement.elements();
            for(Element propertyElement : propertyElements){
                parsePropertyElement(beanDefinition,propertyElement);
            }
            //注册BeanDefinition信息
            this.beanDefinitionRegistry.registerBeanDefinition(beanName,beanDefinition);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
