package com.melo.spring.bean.strategy.surppot;

import com.melo.spring.bean.strategy.ConvertValueStrategy;

public class BooleanStrategy implements ConvertValueStrategy {
    @Override
    public Object convertValue(String value) {
        return Boolean.valueOf(value);
    }
}
