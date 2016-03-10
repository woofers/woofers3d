package com.jaxson.lib.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * An ArrayList that will not add null.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class MyArrayList<E> extends ArrayList<E>
{
	/**
	 * Constructs an empty list with an initial capacity of {@code 10}.
	 */
	public MyArrayList()
	{
		super();
	}

	/**
	 * Constructs a list of an array.
	 * @paeam arrat The array
	 */
	public MyArrayList(E[] array)
	{
		addAll(array);
	}

	/**
	 * Adds element to the list. Does nothing if element is null.
	 * @param e Element to add
	 * @return {@link boolean} - Whether element was added
	 */
	@Override
	public boolean add(E e)
	{
		if (e == null) return false;
		return super.add(e);
	}

	public boolean addAll(E[] array)
	{
		return addAll(new ArrayList<E>(Arrays.asList(array)));
	}

	public int length()
	{
		return size() - 1;
	}

	public void removeRange(int startIndex)
	{
		removeRange(startIndex, size());
	}
}
