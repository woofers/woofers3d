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

public class Game
{
	private GameConfig config;
	private GameStates gameStates;
	private Display display;
	private InputHandler inputHandler;
	private float dt;
	private float accumulator;
	private float step;
	private float clamp;

	public Game(GameConfig config)
	{
		this.config = config;
		this.inputHandler = new InputHandler();
		this.gameStates = new GameStates(this);
		this.display = new Display(this);
		setInputProcessor(InputHandler.getInputProcessor());
		InputHandler.setSensitivity(getConfig().getSensitivity());
	}

	public void dispose()
	{
		gameStates.dispose();
		display.dispose();
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
		return display.getCamera();
	}

	public float getClampInterval()
	{
		return getConfig().getClampInterval();
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
		return display.getGl();
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

	public Viewport getViewport()
	{
		return display.getViewport();
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
		return display.isFocused();
	}

	public boolean isIOS()
	{
		return getApplicationType() == ApplicationType.iOS;
	}

	public boolean isMinimized()
	{
		return display.isMinimized();
	}

	public boolean isMobile()
	{
		return isSmartPhone() || isWeb();
	}

	public boolean isPaused()
	{
		return display.isPaused();
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
		gameStates.pause();
		display.pause();
	}

	public void push(State state)
	{
		gameStates.push(state);
	}

	public void render()
	{
		dt = getDeltaTime();
		if (hasFixedTimeStamp())
		{
			clamp = getClampInterval();
			if (dt > clamp) dt = clamp;
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
		display.render();
		gameStates.render(display.getSpriteBatch(), display.getModelBatch());
		display.drawFps();
	}

	public void resize(int width, int height)
	{
		gameStates.resize(width, height);
		display.resize(width, height);
	}

	public void resume()
	{
		gameStates.resume();
		display.resume();
	}

	public void setCamera(Camera camera)
	{
		display.setCamera(camera);
	}

	public void setInputProcessor(InputProcessor inputProcessor)
	{
		getInput().setInputProcessor(inputProcessor);
	}

	public void setState(State state)
	{
		gameStates.set(state);
	}

	public void setViewport(Viewport viewport)
	{
		display.setViewport(viewport);
	}

	public void update(float step)
	{
		gameStates.update(step);
		display.update(step);
		InputHandler.update(step);
	}
}
