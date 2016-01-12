package com.jaxson.lib.gdx.graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.Disposable;
import com.jaxson.lib.gdx.util.Pauseable;
import com.jaxson.lib.gdx.util.Renderable;

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
