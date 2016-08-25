package com.jaxson.lib.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * An ArrayList that will not add null.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class MyArrayList<E> extends ArrayList<E> implements MyList<E>
{
	private static final long serialVersionUID = -477606372247151069L;

	/**
	 * Constructs an empty list with an initial capacity of {@code 10}.
	 */
	public MyArrayList()
	{
		super();
	}

	/**
	 * Constructs a list of an {@link Collection}.
	 * @parm collection The collection
	 */
	public MyArrayList(Collection<? extends E> collection)
	{
		super(collection);
		removeNull();
	}

	/**
	 * Constructs a list of a array.
	 * @parm array The array
	 */
	public MyArrayList(E... array)
	{
		this(Arrays.asList(array));
	}

	/**
	 * Constructs a list of a specified size.
	 * @parm size The initial size
	 */
	public MyArrayList(int size)
	{
		super(size);
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

	@Override
	public boolean addAll(Collection<? extends E> collection)
	{
		int oldSize = size();
		boolean result = super.addAll(collection);
		if (!result) return false;
		removeNull();
		return oldSize != size();
	}

	/**
	 * Adds element to the list. Does nothing if element is null.
	 * @param e Element to add
	 * @return {@link boolean} - Whether element was added
	 */
	@Override
	public boolean addAll(E[] array)
	{
		return super.addAll(new MyArrayList<>(array));
	}

	@Override
	public int length()
	{
		return size() - 1;
	}

	private void removeNull()
	{
		Iterator<E> iterator = iterator();
		while (iterator.hasNext())
		{
			if (iterator.next() == null) iterator.remove();
		}
	}

	@Override
	public void removeRange(int startIndex)
	{
		removeRange(startIndex, size());
	}
}
