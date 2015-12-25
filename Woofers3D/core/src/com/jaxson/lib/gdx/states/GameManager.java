package com.jaxson.lib.gdx.states;

import com.badlogic.gdx.Gdx;
import com.jaxson.lib.gdx.GameConfig;
import com.jaxson.lib.gdx.input.KeyHandler;

public class GameManager
{
	private GameConfig config;
	private GameStateManager gameStateManager;
	private DisplayManager displayManager;
	private float accumulator;
	private float dt;

	public GameManager(GameConfig config)
	{
		this.config = config;
		this.gameStateManager = new GameStateManager();
		this.displayManager = new DisplayManager(getConfig());
	}

	public void dispose()
	{
		gameStateManager.dispose();
		displayManager.dispose();
	}

	public GameConfig getConfig()
	{
		return config;
	}

	private float getDeltaTime()
	{
		return Gdx.graphics.getDeltaTime();
	}

	public void pause()
	{
		gameStateManager.pause();
		displayManager.pause();
	}

	public void push(State<?> state)
	{
		state.setGameManager(this);
		gameStateManager.push(state);
	}

	public void render()
	{
		dt = getDeltaTime();
		float step = getConfig().getStep();
		if (dt > GameConfig.CLAMP) dt = GameConfig.CLAMP;
		accumulator += dt;
		while (accumulator >= step)
		{
			gameStateManager.update(step);
			displayManager.update(step);
			KeyHandler.update(step);
			accumulator -= step;
		}
		displayManager.render();
		gameStateManager.render(displayManager.getSpriteBatch(), displayManager.getModelBatch());
	}

	public void resize(int width, int height)
	{
		displayManager.resize(width, height);
	}

	public void resume()
	{
		gameStateManager.resume();
		displayManager.resume();
	}
}
