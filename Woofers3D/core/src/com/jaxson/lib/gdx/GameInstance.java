package com.jaxson.lib.gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.gdx.states.State;
import com.jaxson.lib.gdx.util.Pauseable;
import com.jaxson.lib.gdx.util.Resizeable;
import com.jaxson.lib.io.DefaultFile;
import com.jaxson.lib.io.Jsonable;

/**
 * A {@link GameInstance} containing a {@link Game} and a {@link GameConfig}.
 * Extends this and push states to your {@link Game}.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public abstract class GameInstance extends ApplicationAdapter
		implements Pauseable, Resizeable
{
	private Jsonable<GameConfig> config;
	private Game game;

	/**
	 * Constructs a game containing a default {@link GameConfig}.
	 */
	public GameInstance()
	{
		this.config = new Jsonable<>(new DefaultFile("config.json"),
		GameConfig.class,
		new GameConfig());
	}

	/**
	 * Called when the {@link Game} is first created.
	 */
	@Override
	public void create()
	{
		this.game = new Game(getSaveableConfig());
	}

	/**
	 * Called when the {@link Game} is destroyed.
	 * Preceded by a call to {@link #pause()}.
	 */
	@Override
	public void dispose()
	{
		getGame().dispose();
	}

	/**
	 * Gets the config of the {@link Game}.
	 * @return {@link GameConfig} - The config
	 */
	public GameConfig getConfig()
	{
		return getSaveableConfig().get();
	}

	/**
	 * Gets the {@link Game}.
	 * @return {@link Game} - Game
	 */
	public Game getGame()
	{
		return game;
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
	 * Gets the {@link Jsonable} config of the {@link Game}.
	 * @return {@link GameConfig} - The config
	 */
	public Jsonable<GameConfig> getSaveableConfig()
	{
		return config;
	}

	/**
	 * Called when the {@link Game} is paused, usually when it's not active or
	 * visible on screen.
	 * An {@link Game} is also paused before it is destroyed.
	 */
	@Override
	public void pause()
	{
		getGame().pause();
	}

	/**
	 * Pushes a {@link State} to the top {@link Game}.
	 * @param state The state to add
	 */
	public void pushState(State state)
	{
		getGame().pushState(state);
	}

	/**
	 * Called when the {@link Game} should render itself.
	 */
	@Override
	public void render()
	{
		getGame().render();
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
		getGame().resize(width, height);
	}

	/**
	 * Called when the {@link Game} is resumed from a paused state, usually when
	 * it regains focus.
	 */
	@Override
	public void resume()
	{
		getGame().resume();
	}

	/**
	 * Starts the game on desktop.
	 * @param launcher Android launcher to receive the {@link Game}
	 * @deprecated Does nothing
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
