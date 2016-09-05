package com.jaxson.lib.gdx.backend.renderer;

import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.util.GameObject;
import com.jaxson.lib.util.MyArrayList;

public abstract class BaseRenderer<T extends GameObject> extends GameObject implements Renderer<T>
{
	private MyArrayList<T> objects;

	public BaseRenderer()
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

	@Override
	public boolean isEmpty()
	{
		return getObjects().isEmpty();
	}

	@Override
	public void remove(T object)
	{
		getObjects().remove(object);
	}

	@Override
	public abstract void render(View view);

	@Override
	public void update(float dt)
	{
		for (T object: getObjects())
		{
			object.update(dt);
		}
	}

	protected MyArrayList<T> getObjects()
	{
		return objects;
	}
}
