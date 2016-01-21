package com.jaxson.lib.gdx.util;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.Disposable;
import com.jaxson.lib.gdx.Game;

public abstract class GameObject implements Disposable, Renderable, Pauseable
{
	/**
	 * Called when the {@link GameObject} is destroyed.
	 * Preceded by a call to {@link #pause()}.
	 */
	@Override
	public abstract void dispose();

	/**
	 * Called when the {@link GameObject} should check for input.
	 * By default called at the start of the update loop.
	 */
	protected void input()
	{

	}

	/**
	 * Called when the {@link Game} is paused.
	 */
	@Override
	public void pause()
	{

	}

	/**
	 * Called when the {@link GameObject} should update the game logic.
	 * @param spriteBatch SpriteBatch
	 * @param modelBatch ModelBatch
	 * @param camera Camera
	 */
	@Override
	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, Camera camera)
	{

	}

	/**
	 * Called when the {@link Game} is resumed.
	 */
	@Override
	public void resume()
	{

	}

	/**
	 * Called when the {@link GameObject} should render itself.
	 */
	@Override
	public void update(float dt)
	{
		input();
	}
}
