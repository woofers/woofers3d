package com.jaxson.lib.gdx.backend;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Clipboard;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaxson.lib.gdx.GameConfig;
import com.jaxson.lib.gdx.graphics.cameras.TargetCamera;
import com.jaxson.lib.gdx.input.InputHandler;
import com.jaxson.lib.gdx.states.State;

public class GameManager
{
	private GameConfig config;
	private GameStateManager gameStateManager;
	private DisplayManager displayManager;
	private InputHandler inputHandler;
	private float dt;
	private float accumulator;
	private float step;

	public GameManager(GameConfig config)
	{
		this.config = config;
		this.inputHandler = new InputHandler();
		this.gameStateManager = new GameStateManager(this);
		this.displayManager = new DisplayManager(this);
		setInputProcessor(InputHandler.getInputProcessor());
		InputHandler.setSensitivity(getConfig().getSensitivity());
	}

	public void dispose()
	{
		gameStateManager.dispose();
		displayManager.dispose();
	}

	public void exit()
	{
		getApplication().exit();
	}

	public Application getApplication()
	{
		return Gdx.app;
	}

	public ApplicationType getApplicationType()
	{
		return getApplication().getType();
	}

	public Audio getAudio()
	{
		return getApplication().getAudio();
	}

	public Camera getCamera()
	{
		return displayManager.getCamera();
	}

	public Clipboard getClipboard()
	{
		return getApplication().getClipboard();
	}

	public GameConfig getConfig()
	{
		return config;
	}

	private float getDeltaTime()
	{
		return getGraphics().getDeltaTime();
	}

	public Files getFiles()
	{
		return getApplication().getFiles();
	}

	public GL20 getGl()
	{
		return displayManager.getGl();
	}

	public Graphics getGraphics()
	{
		return getApplication().getGraphics();
	}

	public Input getInput()
	{
		return getApplication().getInput();
	}

	public Net getNetwork()
	{
		return getApplication().getNet();
	}

	public float getStepInterval()
	{
		return getConfig().getStepInterval();
	}

	public TargetCamera getTargetCamera()
	{
		return displayManager.getTargetCamera();
	}

	public Viewport getViewport()
	{
		return displayManager.getViewport();
	}

	public boolean hasFixedTimeStamp()
	{
		return getConfig().hasFixedTimeStep();
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

	public boolean isIOS()
	{
		return getApplicationType() == ApplicationType.iOS;
	}

	public boolean isMinimized()
	{
		return displayManager.isMinimized();
	}

	public boolean isMobile()
	{
		return isSmartPhone() || isWeb();
	}

	public boolean isPaused()
	{
		return displayManager.isPaused();
	}

	public boolean isSmartPhone()
	{
		return isAndroid() || isIOS();
	}

	public boolean isWeb()
	{
		return getApplicationType() == ApplicationType.WebGL;
	}

	public void log(String tag, String message)
	{
		getApplication().log(tag, message);
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
		if (dt > GameConfig.CLAMP) dt = GameConfig.CLAMP;
		if (hasFixedTimeStamp())
		{
			step = getStepInterval();
			accumulator += dt;
			while (accumulator >= step)
			{
				update(step);
				accumulator -= step;
			}
		}
		else
		{
			update(dt);
		}
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

	public void update(float step)
	{
		gameStateManager.update(step);
		displayManager.update(step);
		InputHandler.update(step);
	}
}
