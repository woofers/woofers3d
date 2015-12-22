package com.jaxson.lib.gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.jaxson.lib.gdx.states.DisplayManager;
import com.jaxson.lib.gdx.states.State;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public abstract class Game extends ApplicationAdapter
{
	private float accumulator;
	private GameConfig config;
	private DisplayManager displayManager;

	public Game()
	{
		this.config = new GameConfig();
	}

	@Override
	public void create()
	{
		this.displayManager = new DisplayManager(config);
	}

	@Override
	public void dispose()
	{
		displayManager.dispose();
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
		displayManager.pause();
	}

	public void push(State<?> state)
	{
		displayManager.push(state);
	}

	@Override
	public void render()
	{
		float dt = Gdx.graphics.getDeltaTime();
		float step = getConfig().getStep();
		if (dt > GameConfig.CLAMP) dt = GameConfig.CLAMP;
		accumulator += dt;
		while (accumulator >= step)
		{
			displayManager.update(step);
			accumulator -= step;
		}
		displayManager.render();
	}

	@Override
	public void resize(int width, int height)
	{
		displayManager.resize(width, height);
	}

	@Override
	public void resume()
	{
		displayManager.resume();
	}
}
