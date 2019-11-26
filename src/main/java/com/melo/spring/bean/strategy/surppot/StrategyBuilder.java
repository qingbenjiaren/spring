package com.melo.spring.bean.strategy.surppot;

import com.melo.spring.bean.strategy.ConvertValueStrategy;

public class StrategyBuilder {

    public static ConvertValueStrategy buildStrategy(Class<?> type){
        if(type == Integer.class || type == int.class){
            return new IntegerStrategy();
        }else if(type == String.class){
            return new StringStrategy();
        }else if(type == Long.class || type == long.class){
            return new LongStrategy();
        }else if(type == Double.class || type == double.class){
            return new DoubleStrategy();
        }else if(type == Boolean.class || type == boolean.class){
            return new BooleanStrategy();
        }else{
            return null;
        }
    }
}
