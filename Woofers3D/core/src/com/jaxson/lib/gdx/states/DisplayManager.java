package com.jaxson.lib.gdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.gdx.GameConfig;
import com.jaxson.lib.gdx.graphics.GameObject;
import com.jaxson.lib.gdx.input.KeyHandler;
import com.jaxson.lib.gdx.util.GdxMath;

public class DisplayManager extends GameObject
{
	private static final boolean CURSOR_CATCHED = true;

	private GameConfig config;
	private ModelBatch modelBatch;
	private SpriteBatch spriteBatch;
	private boolean fullscreen;
	private boolean focused;

	public DisplayManager(GameConfig config)
	{
		this.config = config;
		this.modelBatch = new ModelBatch();
		this.spriteBatch = new SpriteBatch();

		setCursorCatched(CURSOR_CATCHED);
		clearScreen(GameConfig.CLEAR_COLOR);
		setFullscreen(startsFullscreen());
	}

	public boolean canFullscreen()
	{
		return config.allowsFullscreen();
	}

	public void clearScreen(Color color)
	{
		clearScreen(color.r, color.g, color.b, color.a);
	}

	public void clearScreen(float r, float g, float b, float a)
	{
		getGL().glClearColor(r, g, b, a);
	}

	@Override
	public void dispose()
	{
		spriteBatch.dispose();
		modelBatch.dispose();
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

	private float getDeltaTime()
	{
		return getGraphics().getDeltaTime();
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

	public Input getInput()
	{
		return Gdx.input;
	}

	public ModelBatch getModelBatch()
	{
		return modelBatch;
	}

	public SpriteBatch getSpriteBatch()
	{
		return spriteBatch;
	}

	public int getWidth()
	{
		return getGraphics().getWidth();
	}

	@Override
	protected void input()
	{
		if (canFullscreen() && KeyHandler.isPressed(KeyHandler.FULLSCREEN)) toggleFullscreen();
		if (KeyHandler.justTouched()) setCursorCatched(true);
		if (KeyHandler.isPressed(KeyHandler.ALT))
		{
			if (isCursorCatched()) setCursorPosition(getCenter());
			setCursorCatched(false);
		}
	}

	public boolean isCursorCatched()
	{
		return getInput().isCursorCatched();
	}

	public boolean isFocused()
	{
		return focused && isCursorCatched();
	}

	public boolean isFullscreen()
	{
		return getGraphics().isFullscreen();
	}

	@Override
	public void pause()
	{
		focused = false;
	}

	public void resize(int width, int height)
	{
		//spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, getWidth(), getHeight());
	}

	public void render()
	{
		getGL().glClear(GameConfig.CLEAR_MASK);
	}

	@Override
	public void resume()
	{
		focused = true;
	}

	public void setCursorCatched(boolean catched)
	{
		getInput().setCursorCatched(catched);
	}

	public void setCursorPosition(int x, int y)
	{
		getInput().setCursorPosition(x, y);
	}

	public void setCursorPosition(Vector2 location)
	{
		setCursorPosition((int) location.x, (int) location.y);
	}

	public void setDisplayMode(DisplayMode displayMode)
	{
		getGraphics().setDisplayMode(displayMode);
	}

	public void setDisplayMode(int width, int height)
	{
		setDisplayMode(width, height, false);
	}

	public void setDisplayMode(int width, int height, boolean fullscreen)
	{
		getGraphics().setDisplayMode(width, height, fullscreen);
		updateViewport();
	}

	public void setFullscreen(boolean fullscreen)
	{
		if (fullscreen)
		{
			setDisplayMode(getFullscreenDisplayMode());
		}
		else
		{
			setDisplayMode(getDefaultWidth(), getDefaultHeight());
		}
		KeyHandler.reset();
	}

	public void setViewport(int width, int height)
	{
		setViewport(0, 0, width, height);
	}

	public void setViewport(int x, int y, int width, int height)
	{
		getGL().glViewport(x, y, width, height);
	}

	public boolean startsFullscreen()
	{
		return config.startsFullscreen();
	}

	public void toggleCursorCatched()
	{
		setCursorCatched(!isCursorCatched());
	}

	public void toggleFullscreen()
	{
		setFullscreen(!isFullscreen());
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}

	public void updateViewport()
	{
		setViewport(getWidth(), getHeight());
	}
}
