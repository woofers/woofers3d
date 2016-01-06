package com.jaxson.lib.gdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaxson.lib.gdx.GameConfig;
import com.jaxson.lib.gdx.graphics.GameObject;
import com.jaxson.lib.gdx.graphics.cameras.TargetCamera;
import com.jaxson.lib.gdx.input.KeyHandler;
import com.jaxson.lib.gdx.util.GdxMath;
import com.jaxson.lib.util.MyMath;

public class DisplayManager extends GameObject
{
	private static final int FONT_PADDING = 20;
	private static final Color FPS_COLOR = Color.WHITE;

	private GameManager gameManager;
	private ModelBatch modelBatch;
	private SpriteBatch spriteBatch;
	private BitmapFont font;
	private Viewport viewport;
	private Camera camera;
	private boolean fullscreen;
	private boolean paused;

	public DisplayManager(GameManager gameManager)
	{
		this.gameManager = gameManager;
		this.modelBatch = new ModelBatch();
		this.spriteBatch = new SpriteBatch();
		this.camera = new TargetCamera(getWidth(), getHeight());
		this.viewport = new FillViewport(getWidth(), getHeight(), getCamera());
		this.font = new BitmapFont();

		font.setColor(FPS_COLOR);
		clearScreen(GameConfig.CLEAR_COLOR);
		setFullscreen(startsFullscreen());
	}

	public boolean canFullscreen()
	{
		return getConfig().allowsFullscreen();
	}

	public void clearScreen(Color color)
	{
		clearScreen(color.r, color.g, color.b, color.a);
	}

	public void clearScreen(float r, float g, float b)
	{
		clearScreen(r, g, b, 1);
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

	public void drawFps()
	{
		if (!showsFps()) return;
		getSpriteBatch().begin();
		font.draw(getSpriteBatch(), "Fps: " + getFps(), getOriginX() + FONT_PADDING, getOriginY() + FONT_PADDING);
		getSpriteBatch().end();
		/*
		 * System.out.println("--------------------------");
		 * System.out.println("getBottomGutterHeight()" + ":  " +
		 * getViewport().getBottomGutterHeight());
		 * System.out.println("getLeftGutterWidth()" + ":  " +
		 * getViewport().getLeftGutterWidth());
		 * System.out.println("getRightGutterWidth()" + ":  " +
		 * getViewport().getRightGutterWidth());
		 * System.out.println("getRightGutterX()" + ":  " +
		 * getViewport().getRightGutterX());
		 * System.out.println("getTopGutterHeight()" + ":  " +
		 * getViewport().getTopGutterHeight());
		 * System.out.println("getTopGutterY()" + ":  " +
		 * getViewport().getTopGutterY());
		 * System.out.println("getScreenHeight()" + ":  " +
		 * getViewport().getScreenHeight());
		 * System.out.println("getScreenWidth()" + ":  " +
		 * getViewport().getScreenWidth()); System.out.println("getScreenX()" +
		 * ":  " + getViewport().getScreenX());
		 * System.out.println("getScreenY()" + ":  " +
		 * getViewport().getScreenY());
		 */
	}

	public float getAspectRatio()
	{
		return (float) getWidth() / (float) getHeight();
	}

	public int getBottomGutterHeight()
	{
		return getViewport().getBottomGutterHeight();
	}

	public Camera getCamera()
	{
		return camera;
	}

	public Vector2 getCenter()
	{
		return new Vector2(getWidth() * GdxMath.HALF, getHeight() * GdxMath.HALF);
	}

	public GameConfig getConfig()
	{
		return gameManager.getConfig();
	}

	public float getDefaultAspectRatio()
	{
		return (float) getDefaultWidth() / (float) getDefaultHeight();
	}

	public int getDefaultHeight()
	{
		return getConfig().getHeight();
	}

	public int getDefaultWidth()
	{
		return getConfig().getWidth();
	}

	public DisplayMode getDisplayMode()
	{
		return getGraphics().getDesktopDisplayMode();
	}

	public DisplayMode[] getDisplayModes()
	{
		return getGraphics().getDisplayModes();
	}

	public int getFps()
	{
		return getGraphics().getFramesPerSecond();
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

	public int getLeftGutterWidth()
	{
		return getViewport().getLeftGutterWidth();
	}

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

	public Vector2 getScale(float sourceWidth, float sourceHeight, float targetWidth, float targetHeight)
	{
		float targetRatio = targetHeight / targetWidth;
		float sourceRatio = sourceHeight / sourceWidth;
		Vector2 scale = new Vector2(sourceWidth, sourceHeight);
		float ratio = targetRatio < sourceRatio ? targetWidth / sourceWidth : targetHeight / sourceHeight;
		scale.scl(ratio);
		return scale;
	}

	public SpriteBatch getSpriteBatch()
	{
		return spriteBatch;
	}

	public TargetCamera getTargetCamera()
	{
		if (camera instanceof TargetCamera) return (TargetCamera) camera;
		return null;
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

	public Viewport getViewport()
	{
		return viewport;
	}

	public int getWidth()
	{
		return getGraphics().getWidth();
	}

	@Override
	protected void input()
	{
		if (KeyHandler.isClicked()) setCursorCatched(true);
		if (!isFocused()) return;
		if (!isFullscreen() && isCursorCatched() && KeyHandler.isPressed(Keys.ESCAPE)) setCursorCatched(false);
		if (canFullscreen() && KeyHandler.isDown(KeyHandler.FULLSCREEN)) toggleFullscreen();
	}

	public boolean isCursorCatched()
	{
		return getInput().isCursorCatched();
	}

	public boolean isFocused()
	{
		return !isPaused() && isCursorCatched() || isMobile();
	}

	public boolean isFullscreen()
	{
		return getGraphics().isFullscreen();
	}

	public boolean isMobile()
	{
		return gameManager.isMobile();
	}

	public boolean isPaused()
	{
		return paused;
	}

	@Override
	public void pause()
	{
		paused = true;
	}

	public void render()
	{
		getGL().glClear(GameConfig.CLEAR_MASK);
	}

	public void resize(int width, int height)
	{
		viewport.update(width, height);
		updateSprtieBatch(width, height);
	}

	@Override
	public void resume()
	{
		paused = false;
	}

	public void setCamera(Camera camera)
	{
		this.camera = camera;
		getViewport().setCamera(camera);
	}

	public void setCursorCatched(boolean catched)
	{
		if (catched == isCursorCatched()) return;
		getInput().setCursorCatched(catched);
		if (!catched) setCursorPosition(getCenter());
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
		updateSprtieBatch();
		updateViewport();
	}

	public void setDisplayMode(int width, int height)
	{
		setDisplayMode(width, height, false);
	}

	public void setDisplayMode(int width, int height, boolean fullscreen)
	{
		getGraphics().setDisplayMode(width, height, fullscreen);
		updateSprtieBatch();
		updateViewport();
	}

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

	public void setViewport(Viewport viewport)
	{
		this.viewport = viewport;
		getViewport().setCamera(camera);
	}

	public boolean showsFps()
	{
		return getConfig().showsFps();
	}

	public boolean startsFullscreen()
	{
		return getConfig().startsFullscreen();
	}

	public void toggleCursorCatched()
	{
		setCursorCatched(!isCursorCatched());
	}

	public void toggleFullscreen()
	{
		setFullscreen(!isFullscreen());
		getConfig().setFullscreenStartup(isFullscreen());
		getConfig().save();
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		camera.update();
	}

	public void updateSprtieBatch()
	{
		updateSprtieBatch(getWidth(), getHeight());
	}

	public void updateSprtieBatch(int width, int height)
	{
		getSpriteBatch().getProjectionMatrix().idt();
		getSpriteBatch().getTransformMatrix().idt();
		getSpriteBatch().getProjectionMatrix().setToOrtho2D(0, 0, width, height);
	}

	public void updateViewport()
	{
		setViewport(getWidth(), getHeight());
	}
}
