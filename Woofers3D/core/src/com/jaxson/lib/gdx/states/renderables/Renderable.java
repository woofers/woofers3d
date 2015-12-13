package com.jaxson.lib.gdx.states.renderables;

import com.jaxson.lib.gdx.graphics.GameObject;
import com.jaxson.lib.util.MyArrayList;

public abstract class Renderable<T extends GameObject>
{
	protected MyArrayList<T> objects;

	public Renderable()
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

	public void update(float dt)
	{
		for (T object: objects)
		{
			object.update(dt);
		}
	}
}
