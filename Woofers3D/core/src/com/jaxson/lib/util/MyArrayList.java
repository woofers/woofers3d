package com.jaxson.lib.util;

import java.util.ArrayList;

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
}
