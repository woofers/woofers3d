package com.jaxson.lib.gdx;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.io.DefaultFile;
import com.jaxson.lib.io.File;
import com.jaxson.lib.math.Reciprocal;

/**
 * A universal config file that can be converted to other config types.
 * Such as {@link AndroidApplicationConfiguration} or
 * {@link LwjglApplicationConfiguration}.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class GameConfig
{
	/**
	 * Used in {@link #setBackgroundFps(int)} to pause the {@link Game} on
	 * minimize.
	 */
	public static final int PAUSE_ON_LOST_FOCUS = -1;

	/**
	 * Used in {@link #setStep(int)} to use a variable time step.
	 */
	public static final int VARIBLE_TIME_STEP = -1;

	/**
	 * Used in {@link #setMaxFps(int)} to use a variable frame rate.
	 */
	public static final int VARIBLE_FRAME_RATE = 0;

	private static final FileType ICON_TYPE = FileType.Internal;
	private static final float SENSITIVITY = 1.3f;
	private static final String ICON_PATH = "icon.png";

	private String title = "New Game";
	private int width = 1280;
	private int height = 720;
	private int fps = 600;
	private transient int backgroundFps = PAUSE_ON_LOST_FOCUS;
	private boolean vsync = false;
	private boolean resizable = true;
	private transient int step = 120;
	private transient int clamp = 4;
	private boolean allowFullscreen = true;
	private boolean startFullscreen = false;
	private int antiAliasing = 4;
	private boolean statusBar = false;
	private boolean immersive = true;
	private boolean showFps = true;
	private Vector2 sensitivity = new Vector2(SENSITIVITY, SENSITIVITY);
	private transient File icon = new DefaultFile("icon.png");

	/**
	 * Constructs a default config.
	 */
	public GameConfig()
	{
		this(null);
	}

	/**
	 * Constructs from another config.
	 * @param config The config
	 */
	public GameConfig(GameConfig config)
	{
		set(config);
	}

	/**
	 * Constructs a config from the arguments.
	 * @param width The width of the window
	 * @param height The height of the window
	 * @param fps The target fps of the {@link Game}
	 * @param step The number of logic updates per second in the {@link Game}
	 */
	public GameConfig(int width, int height, int fps, int step)
	{
		setWidth(width);
		setHeight(height);
		setMaxFps(fps);
		setStep(step);
	}

	/**
	 * Gets whether the {@link Game} supports fullscreen.
	 * @return {@link boolean} - Whether the {@link Game} supports fullscreen
	 */
	public boolean allowsFullscreen()
	{
		return allowFullscreen;
	}

	private void autoSave()
	{
	}

	/**
	 * Creates a copy of the {@link GameConfig}.
	 * @return {@link GameConfig} - The copy
	 */
	public GameConfig copy()
	{
		return new GameConfig(this);
	}

	/**
	 * Gets the number of MSAA samples.
	 * Returns {@code 0} when MSAA is off.
	 * @return {@link int} - The number of MSAA samples.
	 */
	public int getAntiAliasing()
	{
		return antiAliasing;
	}

	/**
	 * Gets the frame rate when the game is minimized.
	 * Returns {@code -1} when the game pauses on minimize.
	 * @return {@link int} - The frame rate when the game is minimized.
	 */
	public int getBackgroundFps()
	{
		return backgroundFps;
	}

	/**
	 * Gets the number of logic updates per second that the {@link Game} cannot
	 * skip.
	 * @return {@link int} - The number of logic updates per second that the
	 * {@link Game} cannot skip
	 */
	public int getClamp()
	{
		return clamp;
	}

	/**
	 * Gets the interval between {@link Game} logic updates in milliseconds that
	 * cannot be skipped.
	 * @return {@link float} - The interval between {@link Game} logic updates
	 * in milliseconds that cannot be skipped in milliseconds
	 */
	public float getClampInterval()
	{
		return new Reciprocal(getClamp()).floatValue();
	}

	/**
	 * Gets the starting height of the window in pixels.
	 * @return {@link int} - The starting height of the window in pixels
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * Gets the {@link Game} icon.
	 * @return {@link File} - The {@link Game} icon.
	 */
	public File getIcon()
	{
		return icon;
	}

	/**
	 * Gets the max frame rate.
	 * @return {@link int} - The max frame rate
	 */
	public int getMaxFps()
	{
		return fps;
	}

	/**
	 * Gets the sensitivity of the mouse.
	 * @return {@link Vector2} - The sensitivity of the mouse
	 */
	public Vector2 getSensitivity()
	{
		return sensitivity;
	}

	/**
	 * Gets the number of logic updates per second in the {@link Game}.
	 * @return {@link int} - The number of logic updates per second in the
	 * {@link Game}
	 */
	public int getStep()
	{
		return step;
	}

	/**
	 * Gets the interval between {@link Game} logic updates in milliseconds.
	 * @return {@link float} - The interval between {@link Game} logic updates
	 * in milliseconds
	 */
	public float getStepInterval()
	{
		return new Reciprocal(step).floatValue();
	}

	/**
	 * Gets the title of the {@link Game} window.
	 * @return {@link String} - The title of the {@link Game} window
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Gets the starting width of the window in pixels.
	 * @return {@link int} - The starting width of the window in pixels
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * Gets whether the {@link Game} uses a fixed time step.
	 * @return {@link boolean} - Whether the {@link Game} uses a fixed time
	 * step
	 */
	public boolean hasFixedTimeStep()
	{
		return getStep() != VARIBLE_TIME_STEP;
	}

	/**
	 * Gets whether the {@link Game} has a frame rate cap.
	 * @return {@link boolean} - Whether the {@link Game} has a frame rate cap
	 */
	public boolean hasFpsCap()
	{
		return getMaxFps() != VARIBLE_FRAME_RATE;
	}

	/**
	 * Gets whether the {@link Game} shows a status bar.
	 * @return {@link boolean} - Whether the {@link Game} shows a status bar
	 */
	public boolean hasStatusBar()
	{
		return statusBar;
	}

	/**
	 * Gets whether the {@link Game} uses {@code Kitkat}'s immersive mode.
	 * @return {@link boolean} - Whether the {@link Game} uses immersive mode.
	 * @see <a href=
	 * "http://developer.android.com/training/system-ui/immersive.html">
	 * Immersive Mode</a>
	 */
	public boolean isImmersive()
	{
		return immersive;
	}

	/**
	 * Gets whether the {@link Game} window is resizable.
	 * @return {@link boolean} - Whether the {@link Game} window is resizable
	 */
	public boolean isResizable()
	{
		return resizable;
	}

	/**
	 * Gets whether the {@link Game} uses vertical sync.
	 * @return {@link boolean} - Whether the {@link Game} uses vertical sync
	 */
	public boolean isVsync()
	{
		return vsync;
	}

	public void obtain()
	{

	}

	/**
	 * Sets this instance to another {@link GameConfig}.
	 * @param config The config
	 */
	public void set(GameConfig config)
	{
		if (config == null) return;
		setTitle(config.getTitle());
		setWidth(config.getWidth());
		setHeight(config.getHeight());
		setMaxFps(config.getMaxFps());
		setStep(config.getStep());
		setClamp(config.getClamp());
		setBackgroundFps(config.getBackgroundFps());
		setVsync(config.isVsync());
		setResizable(config.isResizable());
		setFullscreen(config.allowsFullscreen());
		setFullscreenStartup(config.startsFullscreen());
		setStatusBar(config.hasStatusBar());
		setImmersiveMode(config.isImmersive());
		setIcon(config.getIcon());
		setShowFps(config.showsFps());
		setAntiAliasing(config.getAntiAliasing());
		autoSave();
	}

	/**
	 * Sets the number of MSAA samples.
	 * Set to {@code 0} for MSAA to turn off.
	 * @param antiAliasing The number of MSAA samples
	 */
	public void setAntiAliasing(int antiAliasing)
	{
		this.antiAliasing = antiAliasing;
		autoSave();
	}

	/**
	 * Sets the frame rate when the game is minimized.
	 * Set to {@code -1} for the game to pause on minimize.
	 * @param backgroundFps The frame rate when the game is minimized.
	 */
	public void setBackgroundFps(int backgroundFps)
	{
		this.backgroundFps = backgroundFps;
		autoSave();
	}

	/**
	 * Sets the number of logic updates per second that the {@link Game} cannot
	 * skip.
	 * @param clamp The number of logic updates per second that the {@link Game}
	 * cannot skip
	 */
	public void setClamp(int clamp)
	{
		this.clamp = clamp;
	}

	/**
	 * Sets whether the {@link Game} supports fullscreen.
	 * @param allowFullscreen Whether the {@link Game} supports fullscreen
	 */
	public void setFullscreen(boolean allowFullscreen)
	{
		this.allowFullscreen = allowFullscreen;
		autoSave();
	}

	/**
	 * Sets whether the {@link Game} starts fullscreen.
	 * @param startFullscreen Whether the {@link Game} starts fullscreen
	 */
	public void setFullscreenStartup(boolean startFullscreen)
	{
		this.startFullscreen = startFullscreen;
		autoSave();
	}

	/**
	 * Sets the starting height of the window in pixels.
	 * @param height The starting height of the window in pixels
	 */
	public void setHeight(int height)
	{
		this.height = height;
		autoSave();
	}

	/**
	 * Sets the icon of the {@link Game}.
	 * @param icon The icon of the {@link Game}
	 */
	public void setIcon(File icon)
	{
		this.icon = icon;
		autoSave();
	}

	/**
	 * Sets whether the {@link Game} uses {@code Kitkat}'s immersive mode.
	 * @param immersive Whether the {@link Game} uses immersive mode.
	 * @see <a href=
	 * "http://developer.android.com/training/system-ui/immersive.html">
	 * Immersive Mode</a>
	 */
	public void setImmersiveMode(boolean immersive)
	{
		this.immersive = immersive;
		autoSave();
	}

	/**
	 * Sets the max frame rate.
	 * @param fps The max frame rate
	 */
	public void setMaxFps(int fps)
	{
		this.fps = fps;
		autoSave();
	}

	/**
	 * Sets whether the {@link Game} window is resizable.
	 * @param resizable Whether the {@link Game} window is resizable
	 */
	public void setResizable(boolean resizable)
	{
		this.resizable = resizable;
		autoSave();
	}

	/**
	 * Sets sensitivity of the mouse.
	 * @param sensitivity The sensitivity of the mouse
	 */
	public void setSensitivity(float sensitivity)
	{
		getSensitivity().set(sensitivity, sensitivity);
		autoSave();
	}

	/**
	 * Sets sensitivity of the mouse.
	 * Allows separate x and y sensitives.
	 * @param sensitivity The sensitivity of the mouse
	 */
	public void setSensitivity(Vector2 sensitivity)
	{
		this.sensitivity = sensitivity;
		autoSave();
	}

	/**
	 * Gets whether the {@link Game} shows its frame rate.
	 * @param showFps Whether the {@link Game} shows its frame rate
	 */
	public void setShowFps(boolean showFps)
	{
		this.showFps = showFps;
		autoSave();
	}

	/**
	 * Sets whether the {@link Game} shows a status bar.
	 * @param statusBar Whether the {@link Game} shows a status bar
	 */
	public void setStatusBar(boolean statusBar)
	{
		this.statusBar = statusBar;
		autoSave();
	}

	/**
	 * Sets the number of logic updates per second in the {@link Game}.
	 * @param step The number of logic updates per second in the {@link Game}
	 */
	public void setStep(int step)
	{
		this.step = step;
		autoSave();
	}

	/**
	 * Sets the title of the {@link Game} window.
	 * @param title The title of the {@link Game} window
	 */
	public void setTitle(String title)
	{
		this.title = title;
		autoSave();
	}

	/**
	 * Sets whether the {@link Game} uses vertical sync.
	 * @param vsync Whether the {@link Game} uses vertical sync
	 */
	public void setVsync(boolean vsync)
	{
		this.vsync = vsync;
		autoSave();
	}

	/**
	 * Sets the starting width of the window in pixels.
	 * @param width The starting width of the window in pixels
	 */
	public void setWidth(int width)
	{
		this.width = width;
		autoSave();
	}

	/**
	 * Gets whether the {@link Game} shows its frame rate.
	 * @return {@link boolean} - Whether the {@link Game} shows its frame rate
	 */
	public boolean showsFps()
	{
		return showFps;
	}

	/**
	 * Gets whether the {@link Game} starts fullscreen.
	 * @return {@link boolean} - Whether the {@link Game} starts fullscreen
	 */
	public boolean startsFullscreen()
	{
		return startFullscreen;
	}

	/**
	 * Gets the {@link AndroidApplicationConfiguration} of the
	 * {@link GameConfig}.
	 * @return {@link AndroidApplicationConfiguration} - The config.
	 */
	public AndroidApplicationConfiguration toAndroidConfig()
	{
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.hideStatusBar = hasStatusBar();
		config.useImmersiveMode = isImmersive();
		config.numSamples = getAntiAliasing();
		return config;
	}

	/**
	 * Gets the {@link LwjglApplicationConfiguration} of the {@link GameConfig}.
	 * @return {@link LwjglApplicationConfiguration} - The config.
	 */
	public LwjglApplicationConfiguration toLwjglConfig()
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = getTitle();
		config.width = getWidth();
		config.height = getHeight();
		config.vSyncEnabled = isVsync();
		config.foregroundFPS = getMaxFps();
		config.backgroundFPS = getBackgroundFps();
		config.resizable = isResizable();
		config.samples = getAntiAliasing();
		if (getIcon().exists()) config.addIcon(getIcon().getPath(), ICON_TYPE);
		return config;
	}
}
