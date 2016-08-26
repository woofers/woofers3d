package com.jaxson.lib.gdx.backend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Graphics.BufferFormat;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaxson.lib.gdx.GameConfig;
import com.jaxson.lib.gdx.graphics.color.MyColor;
import com.jaxson.lib.gdx.graphics.g2d.Screenshot;
import com.jaxson.lib.gdx.input.InputHandler;
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
	private static final int CLEAR_MASK = GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT;
	private static final int COVERAGE_SAMPLING_MASK = GL20.GL_COVERAGE_BUFFER_BIT_NV;
	private static final int EMPTY_MASK = GL20.GL_ZERO;
	private static final Color CLEAR_COLOR = Color.ROYAL;

	private static final int FONT_PADDING = 20;
	private static final Color FPS_COLOR = Color.WHITE;

	private Game game;
	private ModelBatch modelBatch;
	private SpriteBatch spriteBatch;
	private BitmapFont font;
	private Viewport viewport;
	private Camera camera;
	private boolean minimized;
	private boolean paused;

	/**
	 * Constructs the display.
	 * @param game Reference to the {@link Game}
	 */
	public Display(Game game)
	{
		this.game = game;
		this.modelBatch = new ModelBatch();
		this.spriteBatch = new SpriteBatch();
		this.camera = new OrthographicCamera(getWidth(), getHeight());
		this.viewport = new ExtendViewport(getWidth(), getHeight(), getCamera());
		this.font = new BitmapFont();

		font.setColor(FPS_COLOR);
		setFullscreen(startsFullscreen());
	}

	/**
	 * Gets whether the {@link Game} supports fullscreen.
	 * @return {@link boolean} - Whether the {@link Game} supports fullscreen
	 */
	public boolean canFullscreen()
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
		spriteBatch.dispose();
		modelBatch.dispose();
	}

	public void drawFps()
	{
		if (!showsFps()) return;
		getSpriteBatch().begin();
		font.draw(getSpriteBatch(), "Fps: " + getFps(), getOriginX() + FONT_PADDING, getOriginY() + FONT_PADDING);
		getSpriteBatch().end();
	}

	/**
	 * Gets the aspect ratio of the {@link Game}.
	 * @return {@link float} - The aspect ratio of the {@link Game}
	 */
	public float getAspectRatio()
	{
		return (float) getWidth() / (float) getHeight();
	}

	public int getBottomGutterHeight()
	{
		return getViewport().getBottomGutterHeight();
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
	 * Gets the {@link Camera} of the {@link Display}.
	 * @return {@link Camera} - The {@link Camera} of the {@link Display}
	 */
	public Camera getCamera()
	{
		return camera;
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
	 * Gets the starting height of the {@link Display} in pixels.
	 * @return {@link int} - The starting height of the {@link Display} in
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

	public int getLeftGutterWidth()
	{
		return getViewport().getLeftGutterWidth();
	}

	/**
	 * Gets the {@link ModelBatch} reference.
	 * @return {@link ModelBatch} - The {@link ModelBatch} reference
	 */
	public ModelBatch getModelBatch()
	{
		return modelBatch;
	}

	public Vector2 getOrigin()
	{
		return new Vector2(getOriginX(), getOriginY());
	}

	public int getOriginX()
	{
		return Math.abs((int) (getLeftGutterWidth() * MyMath.reciprocal(getDefaultAspectRatio())));
	}

	public int getOriginY()
	{
		return Math.abs((int) (getBottomGutterHeight() * MyMath.reciprocal(getDefaultAspectRatio())));
	}

	public int getRightGutterWidth()
	{
		return getViewport().getRightGutterWidth();
	}

	public Jsonable<GameConfig> getSaveableConfig()
	{
		return game.getSaveableConfig();
	}

	/**
	 * Gets the {@link SpriteBatch} reference.
	 * @return {@link SpriteBatch} - The {@link SpriteBatch} reference
	 */
	public SpriteBatch getSpriteBatch()
	{
		return spriteBatch;
	}

	public int getTopGutterHeight()
	{
		return getViewport().getTopGutterHeight();
	}

	public int getTotalGutterHeight()
	{
		return getTopGutterHeight() + getBottomGutterHeight();
	}

	public int getTotalGutterWidth()
	{
		return getLeftGutterWidth() + getRightGutterWidth();
	}

	/**
	 * Gets the {@link Viewport} of the {@link Display}.
	 * @return {@link Viewport} - The {@link Viewport} of the {@link Display}
	 */
	public Viewport getViewport()
	{
		return viewport;
	}

	/**
	 * Gets the starting width of the {@link Display} in pixels.
	 * @return {@link int} - The starting width of the {@link Display} in pixels
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

	@Override
	protected void input(float dt)
	{
		if (canFullscreen() && InputHandler.isDown(InputHandler.FULLSCREEN)) toggleFullscreen();
		if (!isCursorCatched() && !isPaused() && InputHandler.justTouched()) setCursorCatched(true);
		if (InputHandler.hasTouchScreen() && InputHandler.threeFingerTouched()) togglePaused();
		if (InputHandler.hasHardwareKeyboard())
		{
			if (InputHandler.isPressed(Keys.ESCAPE)) togglePaused();
			if (InputHandler.isPressed(Keys.F12))
			{
				Screenshot screenshot = new Screenshot();
				screenshot.save();
				screenshot.dispose();
			}
		}
	}

	/**
	 * Gets whether the {@link Cursor} is catched.
	 * @return {@link boolean} - Whether the {@link Cursor} is catched
	 */
	public boolean isCursorCatched()
	{
		return getInput().isCursorCatched();
	}

	/**
	 * Gets whether the {@link Display} is focused.
	 * @return {@link boolean} - Whether the {@link Display} is focused
	 */
	public boolean isFocused()
	{
		return !isMinimized() && isCursorCatched() || isMobile();
	}

	/**
	 * Gets whether the {@link Display} using fullscreen.
	 * @return {@link boolean} - Whether the {@link Display} using fullscreen
	 */
	public boolean isFullscreen()
	{
		return getGraphics().isFullscreen();
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
	 * Gets whether the {@link Game} is mobile.
	 * @return {@link boolean} - Whether the {@link Game} is mobile
	 */
	private boolean isMobile()
	{
		return game.isMobile();
	}

	/**
	 * Gets whether the {@link Display} is paused.
	 * @return {@link boolean} - Whether the {@link Display} is paused
	 */
	public boolean isPaused()
	{
		return paused;
	}

	@Override
	public void pause()
	{
		if (!isPaused()) setCursorCatched(minimized);
		minimized = true;
	}

	public void render()
	{
		clearScreen();
	}

	@Override
	public void resize(int width, int height)
	{
		viewport.update(width, height);
		getSpriteBatch().dispose();
		getModelBatch().dispose();
		spriteBatch = new SpriteBatch();
		modelBatch = new ModelBatch();
	}

	@Override
	public void resume()
	{
		if (!isPaused()) setCursorCatched(minimized);
		minimized = false;
	}

	/**
	 * Sets the {@link Camera} of the {@link Display}.
	 * @param camera The {@link Camera}.
	 */
	public void setCamera(Camera camera)
	{
		this.camera = camera;
		getViewport().setCamera(camera);
	}

	/**
	 * Sets whether the {@link Cursor} is catched.
	 * @param catched Whether the {@link Cursor} should be catched
	 */
	public void setCursorCatched(boolean catched)
	{
		if (catched == isCursorCatched()) return;
		getInput().setCursorCatched(catched);
		if (!catched) setCursorPosition(getCenter());
	}

	/**
	 * Sets the location of the {@link Cursor}.
	 * @param x The x location of the {@link Cursor}
	 * @param x The y location of the {@link Cursor}
	 */
	public void setCursorPosition(int x, int y)
	{
		getInput().setCursorPosition(x, y);
	}

	/**
	 * Sets the location of the {@link Cursor}.
	 * @param location The location of the {@link Cursor}
	 */
	public void setCursorPosition(Vector2 location)
	{
		setCursorPosition((int) location.x, (int) location.y);
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
	 * Sets the {@link DisplayMode} of the {@link Display} by width and height
	 * in windowed mode.
	 * @param width The width
	 * @param height The height
	 */
	public void setDisplayMode(int width, int height)
	{
		setDisplayMode(width, height, false);
	}

	/**
	 * Sets the {@link DisplayMode} of the {@link Display} by width and height.
	 * @param width The width
	 * @param height The height
	 * @param fullscreen Whether the window is fullscreen
	 */
	public void setDisplayMode(int width, int height, boolean fullscreen)
	{
		getGraphics().setDisplayMode(width, height, fullscreen);
		updateViewport();
	}

	/**
	 * Sets whether the {@link Display} uses fullscreen.
	 * @param fullscreen Whether the {@link Display} using fullscreen
	 */
	public void setFullscreen(boolean fullscreen)
	{
		if (isFullscreen() == fullscreen) return;
		if (fullscreen)
		{
			setDisplayMode(getFullscreenDisplayMode());
		}
		else
		{
			setDisplayMode(getDefaultWidth(), getDefaultHeight());
		}
		InputHandler.reset();
	}

	/**
	 * Sets whether the {@link Game} is paused.
	 * @param paused Whether the {@link Game} is paused
	 */
	public void setPaused(boolean paused)
	{
		this.paused = paused;
		setCursorCatched(!isPaused());
	}

	/**
	 * Sets the title of the {@link Game} window.
	 * @param title The title of the {@link Game} window
	 */
	public void setTitle(String title)
	{
		getGraphics().setTitle(title);
		getConfig().setTitle(title);
	}

	public void setViewport(int width, int height)
	{
		setViewport(0, 0, width, height);
	}

	public void setViewport(int x, int y, int width, int height)
	{
		getGl().glViewport(x, y, width, height);
	}

	/**
	 * Sets the {@link Viewport} of the {@link Display}.
	 * @param viewport The {@link Viewport}.
	 */
	public void setViewport(Viewport viewport)
	{
		this.viewport = viewport;
		getViewport().setCamera(camera);
	}

	/**
	 * Sets whether the {@link Game} uses vertical sync.
	 * @param vsync Whether the {@link Game} uses vertical sync
	 */
	public void setVsync(boolean vsync)
	{
		getGraphics().setVSync(vsync);
		getConfig().setVsync(vsync);
	}

	/**
	 * Gets whether the {@link Game} shows its frame rate.
	 * @return {@link boolean} - Whether the {@link Game} shows its frame rate
	 */
	public boolean showsFps()
	{
		return getConfig().showsFps();
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
	 * Toggles whether the {@link Cursor} is catched.
	 */
	public void toggleCursorCatched()
	{
		setCursorCatched(!isCursorCatched());
	}

	/**
	 * Toggles whether the {@link Display} is fullscreen.
	 */
	public void toggleFullscreen()
	{
		setFullscreen(!isFullscreen());
		getConfig().setFullscreenStartup(isFullscreen());
		getSaveableConfig().save();
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
		if (!isPaused()) camera.update();
	}

	/**
	 * Updates the {@link Viewport}
	 */
	public void updateViewport()
	{
		setViewport(getWidth(), getHeight());
	}
}
