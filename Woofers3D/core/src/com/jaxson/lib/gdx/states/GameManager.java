package com.jaxson.lib.gdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.jaxson.lib.gdx.GameConfig;
import com.jaxson.lib.gdx.input.KeyHandler;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaxson.lib.gdx.graphics.cameras.TargetCamera;

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

	public Camera getCamera()
	{
		return displayManager.getCamera();
	}

	public GameConfig getConfig()
	{
		return config;
	}

	private float getDeltaTime()
	{
		return Gdx.graphics.getDeltaTime();
	}

	public TargetCamera getTargetCamera()
	{
		return displayManager.getTargetCamera();
	}

	public Viewport getViewport()
	{
		return displayManager.getViewport();
	}

	public boolean isFocused()
	{
		return displayManager.isFocused();
	}

	public void pause()
	{
		gameStateManager.pause();
		displayManager.pause();
	}

	public void push(State state)
	{
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
			if (isFocused()) gameStateManager.update(step);
			displayManager.update(step);
			KeyHandler.update(step);
			accumulator -= step;
		}
		if (!isFocused()) return;
		displayManager.render();
		gameStateManager.render(displayManager.getSpriteBatch(), displayManager.getModelBatch());
		displayManager.drawFps();
	}

	public void resize(int width, int height)
	{
		displayManager.resize(width, height);
		gameStateManager.resize(width, height);
	}

	public void resume()
	{
		gameStateManager.resume();
		displayManager.resume();
	}

	public void setState(State state)
	{
		gameStateManager.set(state);
	}

	public void setCamera(Camera camera)
	{
		displayManager.setCamera(camera);
	}
	public void setViewport(Viewport viewport)
	{
		displayManager.setViewport(viewport);
	}
}
