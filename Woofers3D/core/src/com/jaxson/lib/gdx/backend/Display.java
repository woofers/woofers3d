package com.jaxson.lib.gdx.backend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Graphics.BufferFormat;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaxson.lib.gdx.GameConfig;
import com.jaxson.lib.gdx.graphics.DisplayOrientation;
import com.jaxson.lib.gdx.graphics.color.MyColor;
import com.jaxson.lib.gdx.graphics.g2d.Screenshot;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.input.Inputs;
import com.jaxson.lib.gdx.input.KeyCombination;
import com.jaxson.lib.gdx.input.Keyboard;
import com.jaxson.lib.gdx.input.KeyboardKey;
import com.jaxson.lib.gdx.input.Keys;
import com.jaxson.lib.gdx.input.Mouse;
import com.jaxson.lib.gdx.input.TouchScreen;
import com.jaxson.lib.gdx.util.GameObject;
import com.jaxson.lib.io.Jsonable;
import com.jaxson.lib.math.MyMath;

/**
 * A class that handles the display and rendering.
 * Contains the {@link Camera}, {@link Viewport}, {@link SpriteBatch},
 * {@link ModelBatch}.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class Display extends GameObject
{
	private static final int CLEAR_MASK =
			GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT;

	private static final int COVERAGE_SAMPLING_MASK =
			GL20.GL_COVERAGE_BUFFER_BIT_NV;

	private static final int EMPTY_MASK = GL20.GL_ZERO;
	private static final Color CLEAR_COLOR = Color.ROYAL;
	private static final int NATIVE_ROTATION_OFFSET = 90;

	private Game game;
	private View view;
	private boolean minimized;
	private boolean paused;
	private int lastWindowedWidth;
	private int lastWindowedHeight;

	private Keyboard keyboard;
	private Mouse mouse;
	private Keys fullscreenKey;
	private KeyboardKey pauseKey;
	private KeyboardKey screenshotKey;
	private TouchScreen touchScreen;

	/**
	 * Constructs the display.
	 * @param game Reference to the {@link Game}
	 */
	public Display(Game game)
	{
		this.game = game;
		this.view = new View(getWidth(), getHeight());
		this.lastWindowedWidth = getDefaultWidth();
		this.lastWindowedHeight = getDefaultHeight();

		this.keyboard = Inputs.getKeyboard();
		this.mouse = Inputs.getMouse();
		this.touchScreen = Inputs.getTouchScreen();
		this.pauseKey = keyboard.getKey("Escape");
		this.screenshotKey = keyboard.getKey("F12");
		this.fullscreenKey =
				new Keys(keyboard.getKey("F11"),
						new KeyCombination(new Keys(keyboard.getKey("L-Alt"),
								keyboard.getKey("R-Alt")),
								keyboard.getKey("Enter")));

		setFullscreen(startsFullscreen());
	}

	/**
	 * Gets whether the {@link Game} supports fullscreen.
	 * @return {@link boolean} - Whether the {@link Game} supports fullscreen
	 */
	public boolean allowsFullscreen()
	{
		return getConfig().allowsFullscreen();
	}

	/**
	 * Clears the screen with the {@link #getClearMask()} and clear color.
	 */
	public void clearScreen()
	{
		clearScreen(CLEAR_COLOR);
		clearScreen(getClearMask());
	}

	/**
	 * Clears the screen with a {@link Color}.
	 * @param color The {@link Color} to clear the screen
	 */
	public void clearScreen(Color color)
	{
		clearScreen(color.r, color.g, color.b, color.a);
	}

	/**
	 * Clears the screen with a RGB color that is fully opaque.
	 * @param r The red conponent of the color
	 * @param g The green conponent of the color
	 * @param b The blue conponent of the color
	 */
	public void clearScreen(float r, float g, float b)
	{
		clearScreen(r, g, b, MyColor.MAX_VALUE_FLOAT);
	}

	/**
	 * Clears the screen with a RGBA color.
	 * @param r The red conponent of the color
	 * @param g The green conponent of the color
	 * @param b The blue conponent of the color
	 * @param a The alpha conponent of the color
	 */
	public void clearScreen(float r, float g, float b, float a)
	{
		getGl().glClearColor(r, g, b, a);
	}

	/**
	 * Clears the screen with a {@link int} mask.
	 * @param mask The clear mask
	 */
	public void clearScreen(int mask)
	{
		getGl().glClear(mask);
	}

	@Override
	public void dispose()
	{
		view.dispose();
	}

	/**
	 * Gets the aspect ratio of the {@link Game}.
	 * @return {@link float} - The aspect ratio of the {@link Game}
	 */
	public float getAspectRatio()
	{
		return (float) getWidth() / (float) getHeight();
	}

	/**
	 * Gets the {@link BufferFormat} of the {@link Display}.
	 * @return {@link BufferFormat} - The {@link BufferFormat} of the
	 * {@link Display}
	 */
	public BufferFormat getBufferFormat()
	{
		return getGraphics().getBufferFormat();
	}

	/**
	 * Gets the center of the {@link Display}.
	 * @return {@link Vector2} - The center of the {@link Display}
	 */
	public Vector2 getCenter()
	{
		return new Vector2(getWidth() * MyMath.HALF, getHeight() * MyMath.HALF);
	}

	/**
	 * Gets the clear mask of the {@link Display}.
	 * @return {@link int} - The clear mask of the {@link Display}
	 */
	public int getClearMask()
	{
		return CLEAR_MASK | getCoverageSampling();
	}

	/**
	 * Gets the config of the {@link Game}.
	 * @return {@link GameConfig} - The config
	 */
	public GameConfig getConfig()
	{
		return game.getConfig();
	}

	/**
	 * Gets the coverage sampling mask.
	 * @return {@link int} - The coverage sampling mask
	 */
	public int getCoverageSampling()
	{
		return hasCoverageSampling() ? COVERAGE_SAMPLING_MASK : EMPTY_MASK;
	}

	/**
	 * Gets the aspect ratio of the {@link Display}.
	 * @return {@link float} - The aspect ratio of the {@link Display}
	 */
	public float getDefaultAspectRatio()
	{
		return (float) getDefaultWidth() / (float) getDefaultHeight();
	}

	/**
	 * Gets the default height of the {@link Display}.
	 * @return {@link float} - The default height of the {@link Display}
	 */
	public int getDefaultHeight()
	{
		return getConfig().getHeight();
	}

	/**
	 * Gets the default width of the {@link Display}.
	 * @return {@link float} - The default width of the {@link Display}
	 */
	public int getDefaultWidth()
	{
		return getConfig().getWidth();
	}

	/**
	 * Gets the {@link DisplayMode} of the {@link Display}.
	 * @return {@link float} - The {@link DisplayMode} of the {@link Display}
	 */
	public DisplayMode getDisplayMode()
	{
		return getGraphics().getDesktopDisplayMode();
	}

	/**
	 * Gets all possible {@link DisplayMode}s of the {@link Display}.
	 * @return {@link float} - All possible {@link DisplayMode}s of the
	 * {@link Display}
	 */
	public DisplayMode[] getDisplayModes()
	{
		return getGraphics().getDisplayModes();
	}

	/**
	 * Gets the frame rate.
	 * @return {@link int} - The frame rate
	 */
	public int getFps()
	{
		return getGraphics().getFramesPerSecond();
	}

	/**
	 * Gets the best fullscreen {@link DisplayMode} of the {@link Display}.
	 * @return {@link DisplayMode} - The best fullscreen {@link DisplayMode} of
	 * the {@link Display}
	 */
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

	/**
	 * Gets the OpenGLES reference.
	 * @return {@link int} - The OpenGLES reference
	 */
	public GL20 getGl()
	{
		return Gdx.gl;
	}

	/**
	 * Gets the {@link Graphics} reference.
	 * @return {@link Graphics} - The {@link Graphics} reference
	 */
	public Graphics getGraphics()
	{
		return game.getGraphics();
	}

	/**
	 * Gets the height of the {@link Display} in pixels.
	 * @return {@link int} - The height of the {@link Display} in
	 * pixels
	 */
	public int getHeight()
	{
		return getGraphics().getHeight();
	}

	/**
	 * Gets the {@link Input} reference.
	 * @return {@link Input} - The {@link Input} reference
	 */
	public Input getInput()
	{
		return game.getInput();
	}

	public int getLastWindowedHeight()
	{
		return lastWindowedHeight;
	}

	public int getLastWindowedWidth()
	{
		return lastWindowedWidth;
	}

	public Orientation getNativeOrientation()
	{
		return getInput().getNativeOrientation();
	}

	public int getNativeRotation()
	{
		return getInput().getRotation();
	}

	public DisplayOrientation getOrientation()
	{
		return new DisplayOrientation(getRotation());
	}

	public int getRotation()
	{
		int rotation = getNativeRotation();
		if (getNativeOrientation() == Orientation.Portrait) return rotation;
		return rotation + NATIVE_ROTATION_OFFSET;
	}

	public Jsonable<GameConfig> getSaveableConfig()
	{
		return game.getSaveableConfig();
	}

	/**
	 * Gets the {@link View} of the {@link Display}.
	 * @return {@link View} - The {@link View} of the {@link Display}
	 */
	public View getView()
	{
		return view;
	}

	/**
	 * Gets the width of the {@link Display} in pixels.
	 * @return {@link int} - The starting of the {@link Display} in pixels
	 */
	public int getWidth()
	{
		return getGraphics().getWidth();
	}

	/**
	 * Gets whether the {@link Display} uses coverage sampling.
	 * @return {@link boolean} - Whether the {@link Display} uses coverage
	 * sampling
	 * @see <a href="http://www.nvidia.com/object/coverage-sampled-aa.html">
	 * CoverageSampling</a>
	 */
	public boolean hasCoverageSampling()
	{
		return getBufferFormat().coverageSampling;
	}

	/**
	 * Gets whether the {@link Display} is focused.
	 * @return {@link boolean} - Whether the {@link Display} is focused
	 */
	public boolean isFocused()
	{
		return !isMinimized() && mouse.isCatched();
	}

	/**
	 * Gets whether the {@link Display} using fullscreen.
	 * @return {@link boolean} - Whether the {@link Display} using fullscreen
	 */
	public boolean isFullscreen()
	{
		return getGraphics().isFullscreen();
	}

	public boolean isLandscape()
	{
		return getOrientation().isLandscape();
	}

	/**
	 * Gets whether the {@link Display} is minimized.
	 * @return {@link boolean} - Whether the {@link Display} is minimized
	 */
	public boolean isMinimized()
	{
		return minimized;
	}

	/**
	 * Gets whether the {@link Display} is paused.
	 * @return {@link boolean} - Whether the {@link Display} is paused
	 */
	public boolean isPaused()
	{
		return paused;
	}

	public boolean isPortrait()
	{
		return getOrientation().isPortrait();
	}

	public boolean isReverseLandscape()
	{
		return getOrientation().equals(DisplayOrientation.REVERSE_LANDSCAPE);
	}

	public boolean isReversePortrait()
	{
		return getOrientation().equals(DisplayOrientation.REVERSE_PORTRAIT);
	}

	public boolean isStandardLandscape()
	{
		return getOrientation().equals(DisplayOrientation.LANDSCAPE);
	}

	public boolean isStandardPortrait()
	{
		return getOrientation().equals(DisplayOrientation.PORTRAIT);
	}

	@Override
	public void pause()
	{
		if (!isPaused()) mouse.setCatched(minimized);
		minimized = true;
	}

	@Override
	public void render(View view)
	{
		clearScreen();
	}

	@Override
	public void resize(int width, int height)
	{
		view.resize(width, height);
	}

	@Override
	public void resume()
	{
		if (!isPaused()) mouse.setCatched(minimized);
		minimized = false;
	}

	/**
	 * Sets the {@link DisplayMode} of the {@link Display}.
	 * @param displayMode The {@link DisplayMode}
	 */
	public void setDisplayMode(DisplayMode displayMode)
	{
		getGraphics().setDisplayMode(displayMode);
		updateViewport();
	}

	/**
	 * Sets the {@link DisplayMode} of the {@link Display} by width and height.
	 * @param width The width
	 * @param height The height
	 */
	public void setDisplayMode(int width, int height)
	{
		getGraphics().setDisplayMode(width, height, false);
		updateViewport();
	}

	/**
	 * Sets whether the {@link Display} uses fullscreen.
	 * @param fullscreen Whether the {@link Display} using fullscreen
	 */
	public void setFullscreen(boolean fullscreen)
	{
		if (isFullscreen() == fullscreen) return;
		getConfig().setFullscreenStartup(fullscreen);
		getSaveableConfig().save();
		if (fullscreen)
		{
			lastWindowedWidth = getWidth();
			lastWindowedHeight = getHeight();
			setDisplayMode(getFullscreenDisplayMode());
		}
		else
		{
			setDisplayMode(getLastWindowedWidth(), getLastWindowedHeight());
		}
		Inputs.reset();
	}

	/**
	 * Sets whether the {@link Game} is paused.
	 * @param paused Whether the {@link Game} is paused
	 */
	public void setPaused(boolean paused)
	{
		this.paused = paused;
		mouse.setCatched(!isPaused());
	}

	/**
	 * Sets the title of the {@link Game} window.
	 * @param title The title of the {@link Game} window
	 */
	public void setTitle(String title)
	{
		getGraphics().setTitle(title);
		getConfig().setTitle(title);
		getSaveableConfig().save();
	}

	/**
	 * Sets whether the {@link Game} uses vertical sync.
	 * @param vsync Whether the {@link Game} uses vertical sync
	 */
	public void setVsync(boolean vsync)
	{
		getGraphics().setVSync(vsync);
		getConfig().setVsync(vsync);
		getSaveableConfig().save();
	}

	/**
	 * Gets whether the {@link Game} starts fullscreen.
	 * @return {@link boolean} - Whether the {@link Game} starts fullscreen
	 */
	public boolean startsFullscreen()
	{
		return getConfig().startsFullscreen();
	}

	/**
	 * Toggles whether the {@link Display} is fullscreen.
	 */
	public void toggleFullscreen()
	{
		setFullscreen(!isFullscreen());
	}

	/**
	 * Toggles whether the {@link Display} is paused.
	 */
	public void togglePaused()
	{
		setPaused(!isPaused());
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		if (!isPaused()) getView().update();
	}

	@Override
	protected void input(float dt)
	{
		if (allowsFullscreen() && fullscreenKey.isDown()) toggleFullscreen();
		if (!mouse.isCatched() && !isPaused() && touchScreen.justTouched())
			mouse.setCatched(true);
		if (touchScreen.exists() && touchScreen.threeFingerTouched())
			togglePaused();
		if (keyboard.exists())
		{
			if (pauseKey.isPressed()
					&& game.getCurrentState() != null
					&& game.getCurrentState().hasPauseState())
				togglePaused();
			if (screenshotKey.isPressed())
			{
				Screenshot screenshot = new Screenshot();
				screenshot.save();
				screenshot.dispose();
			}
		}
	}

	private void setViewport(int width, int height)
	{
		setViewport(0, 0, width, height);
	}

	private void setViewport(int x, int y, int width, int height)
	{
		getGl().glViewport(x, y, width, height);
	}

	/**
	 * Updates the {@link Viewport}
	 */
	private void updateViewport()
	{
		setViewport(getWidth(), getHeight());
	}
}
