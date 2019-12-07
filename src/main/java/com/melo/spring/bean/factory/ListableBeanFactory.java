package com.melo.spring.bean.factory;

import java.util.List;

public interface ListableBeanFactory extends BeanFactory {
    <T>List<T> getBeansByType(Class<?> type);
    <T>List<T> getBeanNamesByType(Class<?> type);
}
