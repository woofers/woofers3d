package com.jaxson.lib.util;

import java.util.List;

public interface MyList<E> extends List<E>, Measurable
{
    public boolean addAll(E[] array);

    public int length();

    public void removeRange(int startIndex);
}
