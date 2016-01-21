package com.jaxson.lib.gdx.util;

import com.jaxson.lib.gdx.Game;

public interface Resizeable
{
	/**
	 * Called when the {@link Game} is resized.
	 * @param width The new width in pixels
	 * @param height The new height in pixels
	 */
	public void resize(int width, int height);
}
