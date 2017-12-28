package com.jaxson.lib.util;

@FunctionalInterface
public interface MyComparable<T> extends Comparable<T>
{
    public static final int LESS = -1;
    public static final int EQUAL = 0;
    public static final int GREATHER = 1;
}
