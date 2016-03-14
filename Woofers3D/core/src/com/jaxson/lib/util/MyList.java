package com.jaxson.lib.util;

import java.util.List;

public interface MyList<E> extends List<E>
{
	public boolean addAll(E[] array);

	public int length();

	public void removeRange(int startIndex);
}
