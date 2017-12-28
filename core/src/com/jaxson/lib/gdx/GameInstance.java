package com.jaxson.lib.gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.gdx.states.State;
import com.jaxson.lib.gdx.util.Pauseable;
import com.jaxson.lib.gdx.util.Resizeable;
import com.jaxson.lib.io.DataFile;
import com.jaxson.lib.io.Json;

/**
 * A {@link GameInstance} containing a {@link Game} and a {@link GameConfig}.
 * Extends this and push states to your {@link Game}.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public abstract class GameInstance extends ApplicationAdapter
        implements Pauseable, Resizeable
{
    private Json<GameConfig> config;
    private Game game;

    /**
     * Constructs a game containing a default {@link GameConfig}.
     */
    public GameInstance()
    {
        this.config = new Json<>(new DataFile("config.json"),
                GameConfig.class,
                new GameConfig());
    }

    /**
     * Gets the config of the {@link Game}.
     * @return {@link GameConfig} - The config
     */
    public GameConfig config()
    {
        return saveableConfig().unwrap();
    }

    /**
     * Called when the {@link Game} is first created.
     */
    @Override
    public void create()
    {
        this.game = new Game(saveableConfig());
    }

    /**
     * Called when the {@link Game} is destroyed.
     * Preceded by a call to {@link #pause()}.
     */
    @Override
    public void dispose()
    {
        game().dispose();
    }

    /**
     * Gets the {@link Game}.
     * @return {@link Game} - Game
     */
    public Game game()
    {
        return game;
    }

    /**
     * Called when the {@link Game} is paused, usually when it's not active or
     * visible on screen.
     * An {@link Game} is also paused before it is destroyed.
     */
    @Override
    public void pause()
    {
        game().pause();
    }

    /**
     * Pushes a {@link State} to the top {@link Game}.
     * @param state The state to add
     */
    public void pushState(State state)
    {
        game().pushState(state);
    }

    /**
     * Called when the {@link Game} should render itself.
     */
    @Override
    public void render()
    {
        game().render();
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
        game().resize(width, height);
    }

    /**
     * Called when the {@link Game} is resumed from a paused state, usually when
     * it regains focus.
     */
    @Override
    public void resume()
    {
        game().resume();
    }

    /**
     * Gets the {@link Json} config of the {@link Game}.
     * @return {@link GameConfig} - The config
     */
    public Json<GameConfig> saveableConfig()
    {
        return config;
    }

    /**
     * Starts the game on android.
     * @param launcher Android launcher to receive the {@link Game}
     * @deprecated Does nothing
     */
    @Deprecated
    public void startAndroid(AndroidApplication launcher)
    {
        // launcher.initialize(this, config().toAndroidConfig());
    }

    /**
     * Starts the game on desktop.
     * @return {@link LwjglApplication} - Instance of the game
     */
    public LwjglApplication startLwjgl()
    {
        return new LwjglApplication(this, config().toLwjglConfig());
    }

    /**
     * Starts the game on desktop.
     * @return {@link Lwjgl3Application} - Instance of the game
     */
    public Lwjgl3Application startLwjgl3()
    {
        return new Lwjgl3Application(this, config().toLwjgl3Config());
    }
}
