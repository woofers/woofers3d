package com.jaxson.lib.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * An ArrayList that will not add null.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class MyArrayList<E> extends ArrayList<E> implements MyList<E>
{
	private static final long serialVersionUID = -477606372247151069L;

	/**
	 * Constructs an empty {@link List} with an initial capacity of {@code 10}.
	 */
	public MyArrayList()
	{
		super();
	}

	/**
	 * Constructs a {@link List} of an {@link Collection}.
	 * @param collection The collection
	 */
	public MyArrayList(Collection<? extends E> collection)
	{
		super(collection);
		removeNull();
	}

	/**
	 * Constructs a {@link List} of a array.
	 * @param array The array
	 */
	public MyArrayList(E... array)
	{
		this(Arrays.asList(array));
	}

	/**
	 * Constructs a {@link List} of a specified size.
	 * @param size The initial size
	 */
	public MyArrayList(int size)
	{
		super(size);
	}

	/**
	 * Adds element to the {@link List}. Does nothing if element is null.
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
	 * Adds element to the {@link List}. Does nothing if element is null.
	 * @param array Elements to add
	 * @return {@link boolean} - Whether element was added
	 */
	@Override
	public boolean addAll(E[] array)
	{
		return super.addAll(new MyArrayList<>(array));
	}

	/**
	 * Adds element to the {@link List}. Does nothing if element is null.
	 * @param list Elements to add
	 * @return {@link boolean} - Whether element was added
	 */
	public boolean addAll(MyArrayList<E> list)
	{
		if (list == null) return false;
		return super.addAll(list);
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

	/**
	 * Removes elements from the {@code startIndex} to the end
	 * of the {@link List}.
	 * @param startIndex Elements to add
	 */
	@Override
	public void removeRange(int startIndex)
	{
		removeRange(startIndex, size());
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + super.toString();
	}
}
