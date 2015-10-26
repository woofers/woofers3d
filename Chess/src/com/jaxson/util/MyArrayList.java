package com.jaxson.util;

import java.util.ArrayList;

public class MyArrayList<E> extends ArrayList<E>
{
	public MyArrayList()
	{
		super();
	}

	@Override
	public boolean add(E e)
	{
		if (e == null) return true;
		super.add(e);
		return true;
	}
}
