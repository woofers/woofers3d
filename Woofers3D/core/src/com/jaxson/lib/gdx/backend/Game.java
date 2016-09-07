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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Clipboard;
import com.jaxson.lib.gdx.GameConfig;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.input.Inputs;
import com.jaxson.lib.gdx.states.State;
import com.jaxson.lib.io.Jsonable;

public class Game
{
	private Jsonable<GameConfig> config;
	private GameStates gameStates;
	private Display display;
	private Inputs inputHandler;
	private float dt;
	private float accumulator;
	private float step;
	private float clamp;

	public Game(Jsonable<GameConfig> config)
	{
		this.config = config;
		this.inputHandler = new Inputs(this);
		this.gameStates = new GameStates(this);
		this.display = new Display(this);
		setInputProcessor(Inputs.getInputProcessor());
		Inputs.getMouse().setSensitivity(getConfig().getSensitivity());
	}

	public void dispose()
	{
		gameStates.dispose();
		getDisplay().dispose();
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
		return getSaveableConfig().get();
	}

	public Display getDisplay()
	{
		return display;
	}

	public Files getFiles()
	{
		return getApplication().getFiles();
	}

	public GL20 getGl()
	{
		return getDisplay().getGl();
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

	public Jsonable<GameConfig> getSaveableConfig()
	{
		return config;
	}

	public float getStepInterval()
	{
		return getConfig().getStepInterval();
	}

	public View getView()
	{
		return getDisplay().getView();
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
		return getDisplay().isFocused();
	}

	public boolean isIOS()
	{
		return getApplicationType() == ApplicationType.iOS;
	}

	public boolean isMinimized()
	{
		return getDisplay().isMinimized();
	}

	public boolean isMobile()
	{
		return isSmartPhone() || isWeb();
	}

	public boolean isPaused()
	{
		return getDisplay().isPaused();
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
		getDisplay().pause();
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
		getDisplay().render(getView());
		gameStates.render(getView());
	}

	public void resize(int width, int height)
	{
		gameStates.resize(width, height);
		getDisplay().resize(width, height);
	}

	public void resume()
	{
		gameStates.resume();
		getDisplay().resume();
	}

	public void setInputProcessor(InputProcessor inputProcessor)
	{
		getInput().setInputProcessor(inputProcessor);
	}

	public void setState(State state)
	{
		gameStates.set(state);
	}

	public void update(float step)
	{
		gameStates.update(step);
		getDisplay().update(step);
		Inputs.update(step);
	}

	private float getDeltaTime()
	{
		return getGraphics().getDeltaTime();
	}
}
