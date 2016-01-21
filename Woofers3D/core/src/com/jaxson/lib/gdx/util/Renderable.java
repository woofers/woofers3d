package com.jaxson.lib.gdx.util;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

public interface Renderable
{
	/**
	 * Called when the {@link Object} should render itself.
	 */
	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, Camera camera);

	/**
	 * Called when the {@link Object} should update the game logic.
	 */
	public void update(float dt);
}
