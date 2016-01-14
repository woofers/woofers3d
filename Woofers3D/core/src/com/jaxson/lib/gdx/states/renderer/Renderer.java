package com.jaxson.lib.gdx.states.renderer;

import com.jaxson.lib.gdx.util.GameObject;
import com.jaxson.lib.util.MyArrayList;

public abstract class Renderer<T extends GameObject>
{
	protected MyArrayList<T> objects;

	public Renderer()
	{
		this.objects = new MyArrayList<T>();
	}

	public void add(T object)
	{
		objects.add(object);
	}

	public void dispose()
	{
		for (T object: objects)
		{
			object.dispose();
		}
	}

	public MyArrayList<T> getObject()
	{
		return objects;
	}

	public boolean isEmpty()
	{
		return objects.isEmpty();
	}

	public void remove(T object)
	{
		objects.remove(object);
	}

	public void update(float dt)
	{
		for (T object: objects)
		{
			object.update(dt);
		}
	}
}
