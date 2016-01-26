package com.jaxson.lib.gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jaxson.lib.gdx.backend.GameManager;
import com.jaxson.lib.gdx.util.Pauseable;
import com.jaxson.lib.gdx.util.Resizeable;
import com.jaxson.lib.gdx.states.State;

/**
 * A Game class containing a {@link GameManager} and a {@link GameConfig}.
 * Extends this and push states to your game.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public abstract class Game extends ApplicationAdapter implements Pauseable, Resizeable
{
	private GameConfig config;
	private GameManager gameManager;

	/**
	 * Constructs a game containing a default {@link GameConfig}.
	 */
	public Game()
	{
		this.config = new GameConfig();
	}

	/**
	 * Called when the {@link Game} is first created.
	 */
	@Override
	public void create()
	{
		this.gameManager = new GameManager(getConfig());
	}

	/**
	 * Called when the {@link Game} is destroyed.
	 * Preceded by a call to {@link #pause()}.
	 */
	@Override
	public void dispose()
	{
		getGameManager().dispose();
	}

	/**
	 * Gets the config of the game.
	 * @return {@link GameConfig} - Config
	 */
	public GameConfig getConfig()
	{
		return config;
	}

	/**
	 * Gets the game manager of the game.
	 * @return {@link GameManager} - Game Manager
	 */
	public GameManager getGameManager()
	{
		return gameManager;
	}

	/**
	 * Gets the Lwjgl config of the game.
	 * @return {@link LwjglApplicationConfiguration} - Lwjgl config
	 */
	public LwjglApplicationConfiguration getLwjglConfig()
	{
		return getConfig().toLwjglConfig();
	}

	/**
	 * Called when the {@link Game} is paused, usually when it's not active or
	 * visible on screen.
	 * An {@link Game} is also paused before it is destroyed.
	 */
	@Override
	public void pause()
	{
		getGameManager().pause();
	}

	/**
	 * Pushes a {@link State} to the top {@link GameManager}.
	 * @param state The state to add
	 */
	public void push(State state)
	{
		getGameManager().push(state);
	}

	/**
	 * Called when the {@link Game} should render itself.
	 */
	@Override
	public void render()
	{
		getGameManager().render();
	}

	/**
	 * Called when the {@link Game} is resized.
	 * This can happen at any point during a non-paused state but will never
	 * happen before a call to {@link #create()}.
	 * @param width The new width in pixels
	 * @param height The new height in pixels
	 */
	@Override
	public void resize(int width, int height)
	{
		getGameManager().resize(width, height);
	}

	/**
	 * Called when the {@link Game} is resumed from a paused state, usually when
	 * it regains focus.
	 */
	@Override
	public void resume()
	{
		getGameManager().resume();
	}

	/**
	 * Starts the game on desktop.
	 * @param launcher Android launcher to receive the {@link Game}
	 * @deprecated not working
	 */
	@Deprecated
	public void startAndroid(AndroidApplication launcher)
	{
		// launcher.initialize(this, toAndroidConfig());
	}

	/**
	 * Starts the game on desktop.
	 * @return {@link LwjglApplication} - Instance of the game
	 */
	public LwjglApplication startDesktop()
	{
		return new LwjglApplication(this, getLwjglConfig());
	}

	/**
	 * Gets the android config of the game.
	 * @return {@link AndroidApplicationConfiguration} - Android config
	 */
	public AndroidApplicationConfiguration toAndroidConfig()
	{
		return getConfig().toAndroidConfig();
	}
}
