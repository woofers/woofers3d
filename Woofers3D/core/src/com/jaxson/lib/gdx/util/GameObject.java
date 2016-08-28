package com.jaxson.lib.gdx.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.Disposable;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.gdx.graphics.views.View;

/**
 * Abstract class to base {@link GameObject}s on.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public abstract class GameObject implements Disposable, Renderable, Pauseable, Resizeable
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
	 * @param dt The delta time
	 */
	protected void input(float dt)
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
	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, View view)
	{

	}

	@Override
	public void resize(int width, int height)
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
	 * @param dt The delta time
	 */
	@Override
	public void update(float dt)
	{
		input(dt);
	}
}
