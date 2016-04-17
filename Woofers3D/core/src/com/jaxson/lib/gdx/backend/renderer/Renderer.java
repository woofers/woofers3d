package com.jaxson.lib.gdx.backend.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.util.GameObject;
import com.jaxson.lib.util.MyArrayList;
import com.jaxson.lib.util.exceptions.NullValueException;

public abstract class Renderer<T extends GameObject> extends GameObject
{
	private MyArrayList<T> objects;

	public Renderer()
	{
		this.objects = new MyArrayList<T>();
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

	protected static void checkAgruments(SpriteBatch spriteBatch, ModelBatch modelBatch, Camera camera)
	{
		if (modelBatch == null) throw new NullValueException("spriteBatch");
		if (spriteBatch == null) throw new NullValueException("modelBatch");
		if (camera == null) throw new NullValueException("camera");
	}
}
