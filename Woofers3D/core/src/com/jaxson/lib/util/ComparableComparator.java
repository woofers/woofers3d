package com.jaxson.lib.util;

import java.util.Comparator;

public class ComparableComparator<T extends Comparable<T>> implements Comparator<T>
{
	@Override
	public int compare(T object1, T object2)
	{
		return object1.compareTo(object2);
	}
}
