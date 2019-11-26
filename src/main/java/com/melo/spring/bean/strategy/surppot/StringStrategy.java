package com.melo.spring.bean.strategy.surppot;

import com.melo.spring.bean.strategy.ConvertValueStrategy;

public class StringStrategy implements ConvertValueStrategy {

    @Override
    public String convertValue(String value) {
        return value;
    }
}
