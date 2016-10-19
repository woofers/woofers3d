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
import com.jaxson.lib.io.Json;

public class Game
{
	private Json<GameConfig> config;
	private GameStates gameStates;
	private Display display;
	private Inputs inputs;
	private float dt;
	private float accumulator;
	private float step;
	private float clamp;

	public Game(Json<GameConfig> config)
	{
		this.config = config;
		this.inputs = new Inputs(this);
		this.gameStates = new GameStates(this);
		this.display = new Display(this);
	}

	public Application application()
	{
		return Gdx.app;
	}

	public ApplicationType applicationType()
	{
		return application().getType();
	}

	public Audio audio()
	{
		return application().getAudio();
	}

	public float clampInterval()
	{
		return config().getClampInterval();
	}

	public Clipboard clipboard()
	{
		return application().getClipboard();
	}

	public GameConfig config()
	{
		return saveableConfig().unwrap();
	}

	public State currentState()
	{
		return gameStates.peek();
	}

	private float deltaTime()
	{
		return graphics().getDeltaTime();
	}

	public Display display()
	{
		return display;
	}

	public void dispose()
	{
		gameStates.dispose();
		display().dispose();
	}

	public void exit()
	{
		application().exit();
	}

	public Files files()
	{
		return application().getFiles();
	}

	public GL20 gl()
	{
		return display().gl();
	}

	public Graphics graphics()
	{
		return application().getGraphics();
	}

	public boolean hasFixedTimeStamp()
	{
		return config().hasFixedTimeStep();
	}

	public Input input()
	{
		return application().getInput();
	}

	public boolean isAndroid()
	{
		return applicationType() == ApplicationType.Android;
	}

	public boolean isDesktop()
	{
		return applicationType() == ApplicationType.Desktop;
	}

	public boolean isFocused()
	{
		return display().isFocused();
	}

	public boolean isIOS()
	{
		return applicationType() == ApplicationType.iOS;
	}

	public boolean isMinimized()
	{
		return display().isMinimized();
	}

	public boolean isMobile()
	{
		return isSmartPhone() || isWeb();
	}

	public boolean isPaused()
	{
		return display().isPaused();
	}

	public boolean isSmartPhone()
	{
		return isAndroid() || isIOS();
	}

	public boolean isWeb()
	{
		return applicationType() == ApplicationType.WebGL;
	}

	public void log(String tag, String message)
	{
		application().log(tag, message);
	}

	public Net network()
	{
		return application().getNet();
	}

	public void pause()
	{
		gameStates.pause();
		display().pause();
	}

	public void pushState(State state)
	{
		gameStates.push(state);
	}

	public void render()
	{
		dt = deltaTime();
		if (hasFixedTimeStamp())
		{
			clamp = clampInterval();
			if (dt > clamp) dt = clamp;
			step = stepInterval();
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
		display().render(view());
		gameStates.render(view());
	}

	public void resize(int width, int height)
	{
		gameStates.resize(width, height);
		display().resize(width, height);
	}

	public void resume()
	{
		gameStates.resume();
		display().resume();
	}

	public Json<GameConfig> saveableConfig()
	{
		return config;
	}

	public void setInputProcessor(InputProcessor inputProcessor)
	{
		input().setInputProcessor(inputProcessor);
	}

	public void setState(State state)
	{
		gameStates.set(state);
	}

	public float stepInterval()
	{
		return config().getStepInterval();
	}

	public void update(float step)
	{
		gameStates.update(step);
		display().update(step);
		Inputs.update(step);
	}

	public View view()
	{
		return display().view();
	}
}
