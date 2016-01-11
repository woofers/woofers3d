package com.jaxson.lib.gdx.graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.Disposable;
import com.jaxson.lib.gdx.util.Renderable;
import com.jaxson.lib.gdx.util.Pauseable;

public abstract class GameObject implements Disposable, Renderable, Pauseable
{
	public abstract void dispose();

	protected void input()
	{

	}

	public void pause()
	{

	}

	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, Camera camera)
	{

	}

	public void resume()
	{

	}

	public void update(float dt)
	{
		input();
	}
}
