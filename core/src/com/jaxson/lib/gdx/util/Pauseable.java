package com.jaxson.lib.gdx.util;

import com.jaxson.lib.gdx.backend.Game;

/**
 * Interface for pauseable {@link Object}s.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
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
