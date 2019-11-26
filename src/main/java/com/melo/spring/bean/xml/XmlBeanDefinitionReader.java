package com.melo.spring.bean.xml;


import com.melo.spring.bean.factory.registry.BeanDefinitionRegistry;
import com.melo.spring.bean.utils.DocumentReader;
import org.dom4j.Document;

import java.io.InputStream;

/**
 * 对XML文件进行加载，然后获取BeanDefinition
 */
public class XmlBeanDefinitionReader {
    private BeanDefinitionRegistry beanDefinitionRegistry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry){
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void loadBeanDefinitions(InputStream inputStream){
        Document document = DocumentReader.createDocument(inputStream);
        XmlBeanDefinitionDocumentReader xmlBeanDefinitionDocumentReader = new XmlBeanDefinitionDocumentReader(beanDefinitionRegistry);
        xmlBeanDefinitionDocumentReader.registerBeanDefinitions(document.getRootElement());
    }
}
