package com.melo.spring.bean.strategy.surppot;

import com.melo.spring.bean.strategy.ConvertValueStrategy;

public class IntegerStrategy implements ConvertValueStrategy {
    @Override
    public Object convertValue(String value) {
        return Integer.parseInt(value);
    }
}
