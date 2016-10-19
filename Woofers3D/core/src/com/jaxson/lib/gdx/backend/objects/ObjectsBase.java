package com.jaxson.lib.gdx.backend.objects;

import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.util.GameObject;
import com.jaxson.lib.util.MyArrayList;

public abstract class ObjectsBase<T extends GameObject>
		extends GameObject implements GameObjects<T>
{
	private MyArrayList<T> objects;

	public ObjectsBase()
	{
		this.objects = new MyArrayList<>();
	}

	@Override
	public void add(T object)
	{
		getObjects().add(object);
	}

	@Override
	public void dispose()
	{
		for (T object: getObjects())
		{
			object.dispose();
		}
	}

	protected MyArrayList<T> getObjects()
	{
		return objects;
	}

	@Override
	public boolean isEmpty()
	{
		return getObjects().isEmpty();
	}

	@Override
	public void pause()
	{
		for (T object: getObjects())
		{
			object.pause();
		}
	}

	@Override
	public void remove(T object)
	{
		getObjects().remove(object);
	}

	@Override
	public abstract void render(View view);

	@Override
	public void resize(int width, int height)
	{
		for (T object: getObjects())
		{
			object.resize(width, height);
		}
	}

	@Override
	public void resume()
	{
		for (T object: getObjects())
		{
			object.resume();
		}
	}

	@Override
	public void update(float dt)
	{
		for (T object: getObjects())
		{
			object.update(dt);
		}
	}
}
