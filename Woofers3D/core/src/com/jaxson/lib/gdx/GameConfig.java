package com.jaxson.lib.gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class GameConfig
{
	private static final String TITLE = "Title";
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 768;
	private static final int FPS = 300;
	private static final int BACKGROUND_FPS = -1;
	private static final boolean VSYNC = false;
	private static final boolean RESIZABLE = false;
	private static final boolean ALLOW_FULLSCREEN = true;
	private static final float STEP = 1f / 120f;

	public static final float CLAMP = 1f / 4f;
	public static final int CLEAR_MASK = GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT;
	public static final Color CLEAR_COLOR = new Color(0f, 0f, 1f, 1f);

	private String title;
	private int width;
	private int height;
	private int fps;
	private int backgroundFps;
	private boolean vsync;
	private boolean resizable;
	private float step;
	private boolean allowFullscreen;

	public GameConfig()
	{
		this(WIDTH, HEIGHT, FPS, STEP);
	}

	public GameConfig(int width, int height, int fps, float step)
	{
		setWidth(width);
		setHeight(height);
		setFps(fps);
		setStep(step);
		setTitle(TITLE);
		setBackgroundFps(BACKGROUND_FPS);
		setVsync(VSYNC);
		setResizable(RESIZABLE);
		setFullscreen(ALLOW_FULLSCREEN);
	}

	public boolean canFullscreen()
	{
		return allowFullscreen;
	}

	public int getBackgroundFps()
	{
		return backgroundFps;
	}

	public int getFps()
	{
		return fps;
	}

	public int getHeight()
	{
		return height;
	}

	public float getStep()
	{
		return step;
	}

	public String getTitle()
	{
		return title;
	}

	public int getWidth()
	{
		return width;
	}

	public boolean isResizable()
	{
		return resizable;
	}

	public boolean isVsync()
	{
		return vsync;
	}

	public void setBackgroundFps(int backgroundFps)
	{
		this.backgroundFps = backgroundFps;
	}

	public void setFps(int fps)
	{
		this.fps = fps;
	}

	public void setFullscreen(boolean allowFullscreen)
	{
		this.allowFullscreen = allowFullscreen;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public void setResizable(boolean resizable)
	{
		this.resizable = resizable;
	}

	public void setStep(float step)
	{
		this.step = step;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setVsync(boolean vsync)
	{
		this.vsync = vsync;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public LwjglApplicationConfiguration toLwjglConfig()
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = getTitle();
		config.width = getWidth();
		config.height = getHeight();
		config.vSyncEnabled = isVsync();
		config.foregroundFPS = getFps();
		config.backgroundFPS = getBackgroundFps();
		config.resizable = isResizable();
		return config;
	}
}
