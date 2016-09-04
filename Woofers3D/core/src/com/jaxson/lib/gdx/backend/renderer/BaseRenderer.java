package com.jaxson.lib.gdx.backend.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.util.GameObject;
import com.jaxson.lib.util.MyArrayList;
import com.jaxson.lib.util.exceptions.NullValueException;
import com.jaxson.lib.gdx.graphics.views.View;

public abstract class BaseRenderer<T extends GameObject> extends GameObject implements Renderer<T>
{
	private MyArrayList<T> objects;

	public BaseRenderer()
	{
		this.objects = new MyArrayList<>();
	}

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

	public boolean isEmpty()
	{
		return getObjects().isEmpty();
	}

	public abstract void render(View view);

	public void remove(T object)
	{
		getObjects().remove(object);
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
