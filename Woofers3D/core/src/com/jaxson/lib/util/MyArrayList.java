package com.jaxson.lib.util;

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
		if (e == null) return false;
		return super.add(e);
	}
}
