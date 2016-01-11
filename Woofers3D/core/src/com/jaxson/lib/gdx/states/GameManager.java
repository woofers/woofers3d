package com.jaxson.lib.gdx.states;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaxson.lib.gdx.GameConfig;
import com.jaxson.lib.gdx.graphics.cameras.TargetCamera;
import com.jaxson.lib.gdx.input.InputHandler;

public class GameManager
{
	private GameConfig config;
	private GameStateManager gameStateManager;
	private DisplayManager displayManager;
	private InputHandler inputHandler;
	private float accumulator;
	private float dt;

	public GameManager(GameConfig config)
	{
		this.config = config;
		this.inputHandler = new InputHandler();
		this.gameStateManager = new GameStateManager();
		this.displayManager = new DisplayManager(this);
		setInputProcessor(InputHandler.getInputProcessor());
		InputHandler.setSensitivity(getConfig().getSensitivity());
	}

	public void dispose()
	{
		gameStateManager.dispose();
		displayManager.dispose();
	}

	public Application getApplication()
	{
		return Gdx.app;
	}

	public ApplicationType getApplicationType()
	{
		return getApplication().getType();
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

	private Input getInput()
	{
		return Gdx.input;
	}

	public TargetCamera getTargetCamera()
	{
		return displayManager.getTargetCamera();
	}

	public Viewport getViewport()
	{
		return displayManager.getViewport();
	}

	public boolean isAndroid()
	{
		return getApplicationType() == ApplicationType.Android;
	}

	public boolean isDesktop()
	{
		return getApplicationType() == ApplicationType.Desktop;
	}

	public boolean isFocused()
	{
		return displayManager.isFocused();
	}

	public boolean isPaused()
	{
		return displayManager.isPaused();
	}

	public boolean isIOS()
	{
		return getApplicationType() == ApplicationType.iOS;
	}

	public boolean isMobile()
	{
		return isSmartPhone() || isWeb();
	}

	public boolean isSmartPhone()
	{
		return isAndroid() || isIOS();
	}

	public boolean isWeb()
	{
		return getApplicationType() == ApplicationType.WebGL;
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
			gameStateManager.update(step, isFocused());
			displayManager.update(step);
			InputHandler.update(step);
			accumulator -= step;
		}
		if (!isFocused()) return;
		displayManager.render();
		gameStateManager.render(displayManager.getSpriteBatch(), displayManager.getModelBatch(), isFocused());
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

	public void setCamera(Camera camera)
	{
		displayManager.setCamera(camera);
	}

	public void setInputProcessor(InputProcessor inputProcessor)
	{
		getInput().setInputProcessor(inputProcessor);
	}

	public void setState(State state)
	{
		gameStateManager.set(state);
	}

	public void setViewport(Viewport viewport)
	{
		displayManager.setViewport(viewport);
	}
}
