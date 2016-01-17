package com.jaxson.lib.gdx.states.renderer;

import com.jaxson.lib.gdx.util.GameObject;
import com.jaxson.lib.util.MyArrayList;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.Camera;

public abstract class Renderer<T extends GameObject> extends GameObject
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

	@Override
	public void dispose()
	{
		for (T object: objects)
		{
			object.dispose();
		}
	}

	protected void checkAgruments(SpriteBatch spriteBatch, ModelBatch modelBatch, Camera camera)
	{
		if (modelBatch == null) throw new IllegalArgumentException("modelBatch cannot be null");
		if (spriteBatch == null) throw new IllegalArgumentException("spriteBatch cannot be null");
		if (camera == null) throw new IllegalArgumentException("camera cannot be null");
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

	@Override
	public void update(float dt)
	{
		for (T object: objects)
		{
			object.update(dt);
		}
	}
}
