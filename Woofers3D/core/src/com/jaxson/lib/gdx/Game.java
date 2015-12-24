package com.jaxson.lib.gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jaxson.lib.gdx.states.State;
import com.jaxson.lib.gdx.states.DisplayManager;
import com.jaxson.lib.gdx.states.GameManager;

public abstract class Game extends ApplicationAdapter
{
	private GameConfig config;
	private GameManager gameManager;

	public Game()
	{
		this.config = new GameConfig();
	}

	@Override
	public void create()
	{
		this.gameManager = new GameManager(getConfig());
	}

	@Override
	public void dispose()
	{
		gameManager.dispose();
	}

	public GameConfig getConfig()
	{
		return config;
	}

	public LwjglApplicationConfiguration getLwjglConfig()
	{
		return getConfig().toLwjglConfig();
	}

	@Override
	public void pause()
	{
		gameManager.pause();
	}

	public void push(State<?> state)
	{
		gameManager.push(state);
	}

	@Override
	public void render()
	{
		gameManager.render();
	}

	@Override
	public void resize(int width, int height)
	{
		gameManager.resize(width, height);
	}

	@Override
	public void resume()
	{
		gameManager.resume();
	}

	public LwjglApplication startDesktop()
	{
		return new LwjglApplication(this, getLwjglConfig());
	}
}
