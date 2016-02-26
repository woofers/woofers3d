package com.jaxson.lib.util;

import java.util.Comparator;
import com.jaxson.lib.util.MyComparable;

public class ComparableComparator<T extends MyComparable<T>> implements Comparator<T>
{
	@Override
	public int compare(T object1, T object2)
	{
		return object1.compareTo(object2);
	}
}
