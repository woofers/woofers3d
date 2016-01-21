package com.jaxson.lib.gdx.util;

import com.jaxson.lib.gdx.Game;

public interface Pauseable
{
	/**
	 * Called when the {@link Game} is paused.
	 */
	public void pause();

	/**
	 * Called when the {@link Game} is resumed.
	 */
	public void resume();
}
