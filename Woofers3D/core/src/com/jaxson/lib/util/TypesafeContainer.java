package com.jaxson.lib.util;

public class TypesafeContainer
{
    private Map<Class<?>, Object> dateMap = new HashMap<Class<?>, Object>();

    public <T> void putDate(Class<T> type, T instance)
    {
          if(type == null) throw new NullPointerException("Type null");
          dateMap.put(type, instance);
    }

    public<T> getDate(Class<T> type){
          return type.cast(dateMap.get(type));
    }
}