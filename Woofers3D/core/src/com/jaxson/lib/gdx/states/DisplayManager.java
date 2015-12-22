package com.jaxson.lib.gdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.gdx.GameConfig;
import com.jaxson.lib.gdx.util.GdxMath;
import com.jaxson.lib.gdx.input.KeyHandler;

public class DisplayManager
{
	private GameStateManager gameStateManager;
	private ModelBatch modelBatch;
	private SpriteBatch spriteBatch;
	private GameConfig config;
	private boolean fullscreen;

	public DisplayManager(GameConfig config)
	{
		this.config = config;
		this.gameStateManager = new GameStateManager();
		this.modelBatch = new ModelBatch();
		this.spriteBatch = new SpriteBatch();
		getGL().glClearColor(GameConfig.CLEAR_COLOR.r, GameConfig.CLEAR_COLOR.g, GameConfig.CLEAR_COLOR.b, GameConfig.CLEAR_COLOR.a);
	}

	public boolean canFullscreen()
	{
		return config.canFullscreen();
	}

	public void dispose()
	{
		spriteBatch.dispose();
		modelBatch.dispose();
		getGameStateManager().dispose();
	}

	public Vector2 getCenter()
	{
		return new Vector2(getWidth() * GdxMath.HALF, getHeight() * GdxMath.HALF);
	}

	public int getDefaultHeight()
	{
		return config.getHeight();
	}

	public int getDefaultWidth()
	{
		return config.getWidth();
	}

	public DisplayMode getDisplayMode()
	{
		return getGraphics().getDesktopDisplayMode();
	}

	public DisplayMode[] getDisplayModes()
	{
		return getGraphics().getDisplayModes();
	}

	public DisplayMode getFullscreenDisplayMode()
	{
		DisplayMode[] displayModes = getDisplayModes();
		DisplayMode bestMode = displayModes[0];
		for (DisplayMode mode: displayModes)
		{
			if (bestMode.width < mode.width) bestMode = mode;
		}
		return bestMode;
	}

	public GameStateManager getGameStateManager()
	{
		return gameStateManager;
	}

	public GL20 getGL()
	{
		return Gdx.gl;
	}

	public Graphics getGraphics()
	{
		return Gdx.graphics;
	}

	public int getHeight()
	{
		return getGraphics().getHeight();
	}

	public int getWidth()
	{
		return getGraphics().getWidth();
	}

	public boolean isFullscreen()
	{
		return fullscreen;
	}

	public void pause()
	{
		getGameStateManager().pause();
	}

	public void pop()
	{
		getGameStateManager().pop();
	}

	public void push(State<?> state)
	{
		state.setDisplayManager(this);
		getGameStateManager().push(state);
	}

	public void render()
	{
		getGL().glClear(GameConfig.CLEAR_MASK);
		getGameStateManager().render(spriteBatch, modelBatch);
	}

	public void resize(int width, int height)
	{
		spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, getWidth(), getHeight());
	}

	public void resume()
	{
		getGameStateManager().resume();
	}

	public void set(State<?> state)
	{
		getGameStateManager().set(state);
	}

	public void setDisplayMode(DisplayMode displayMode)
	{
		getGraphics().setDisplayMode(displayMode);
	}

	public void setDisplayMode(int width, int height, boolean fullscreen)
	{
		getGraphics().setDisplayMode(width, height, fullscreen);
		updateViewport();
	}

	public void setFullscreen(boolean fullscreen)
	{
		this.fullscreen = fullscreen;
		if (isFullscreen())
		{
			setDisplayMode(getFullscreenDisplayMode());
		}
		else
		{
			setDisplayMode(config.getWidth(), config.getHeight(), false);
		}
	}

	public void setViewport(int width, int height)
	{
		setViewport(0, 0, width, height);
	}

	public void setViewport(int x, int y, int width, int height)
	{
		getGL().glViewport(x, y, width, height);
	}

	public void toggleFullscreen()
	{
		setFullscreen(!isFullscreen());
	}

	public void update(float dt)
	{
		if (canFullscreen() && KeyHandler.isPressed(KeyHandler.FULLSCREEN)) toggleFullscreen();
		getGameStateManager().update(dt);
	}

	public void updateViewport()
	{
		setViewport(getWidth(), getHeight());
	}
}
