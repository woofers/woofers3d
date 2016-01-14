package com.jaxson.lib.gdx.util;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.Disposable;

public abstract class GameObject implements Disposable, Renderable, Pauseable
{
	@Override
	public abstract void dispose();

	protected void input()
	{

	}

	@Override
	public void pause()
	{

	}

	@Override
	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, Camera camera)
	{

	}

	@Override
	public void resume()
	{

	}

	@Override
	public void update(float dt)
	{
		input();
	}
}
