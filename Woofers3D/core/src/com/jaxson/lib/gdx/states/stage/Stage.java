package com.jaxson.lib.gdx.states.stage;

import com.jaxson.lib.util.MyArrayList;

public abstract class Stage<T>
{
	protected MyArrayList<T> objects;

	public Stage()
	{
		this.objects = new MyArrayList<T>();
	}

	public void add(T object)
	{
		objects.add(object);
	}

	public abstract void dispose();

	public boolean isEmpty()
	{
		return objects.isEmpty();
	}

	public MyArrayList<T> getObject()
	{
		return objects;
	}

	public void remove(T object)
	{
		objects.remove(object);
	}

	public abstract void update(float dt);
}
