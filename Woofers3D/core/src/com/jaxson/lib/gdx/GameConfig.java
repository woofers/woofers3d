package com.jaxson.lib.gdx;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.io.DataFile;
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

	/**
	 * Used in {@link #setX(int)} and {@link #setY(int)} to center the window.
	 */
	public static final int WINDOW_CENTER = -1;

	private static final FileType ICON_TYPE = FileType.Internal;
	private static final float SENSITIVITY = 1.3f;
	private static final String ICON_PATH = "icon.png";

	private String title = "New Game";
	private int width = 1280;
	private int height = 720;
	private int x = WINDOW_CENTER;
	private int y = WINDOW_CENTER;
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
	private Vector2 sensitivity = new Vector2(SENSITIVITY, SENSITIVITY);
	private transient File icon = new DataFile("icon.png");
	private boolean gl30 = true;

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

	/**
	 * Gets the number of MSAA samples.
	 * Returns {@code 0} when MSAA is off.
	 * @return {@link int} - The number of MSAA samples.
	 */
	public int antiAliasing()
	{
		return antiAliasing;
	}

	private void autoSave()
	{
	}

	/**
	 * Gets the frame rate when the game is minimized.
	 * Returns {@code -1} when the game pauses on minimize.
	 * @return {@link int} - The frame rate when the game is minimized.
	 */
	public int backgroundFps()
	{
		return backgroundFps;
	}

	public void center()
	{
		setX(WINDOW_CENTER);
		setY(WINDOW_CENTER);
	}

	/**
	 * Gets the number of logic updates per second that the {@link Game} cannot
	 * skip.
	 * @return {@link int} - The number of logic updates per second that the
	 * {@link Game} cannot skip
	 */
	public int clamp()
	{
		return clamp;
	}

	/**
	 * Gets the interval between {@link Game} logic updates in milliseconds that
	 * cannot be skipped.
	 * @return {@link float} - The interval between {@link Game} logic updates
	 * in milliseconds that cannot be skipped in milliseconds
	 */
	public float clampInterval()
	{
		return new Reciprocal(clamp()).floatValue();
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
	 * Gets whether the {@link Game} uses a fixed time step.
	 * @return {@link boolean} - Whether the {@link Game} uses a fixed time
	 * step
	 */
	public boolean hasFixedTimeStep()
	{
		return step() != VARIBLE_TIME_STEP;
	}

	/**
	 * Gets whether the {@link Game} has a frame rate cap.
	 * @return {@link boolean} - Whether the {@link Game} has a frame rate cap
	 */
	public boolean hasFpsCap()
	{
		return maxFps() != VARIBLE_FRAME_RATE;
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
	 * Gets the starting height of the window in pixels.
	 * @return {@link int} - The starting height of the window in pixels
	 */
	public int height()
	{
		return height;
	}

	/**
	 * Gets the {@link Game} icon.
	 * @return {@link File} - The {@link Game} icon.
	 */
	public File icon()
	{
		return icon;
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

	/**
	 * Gets the max frame rate.
	 * @return {@link int} - The max frame rate
	 */
	public int maxFps()
	{
		return fps;
	}

	public void obtain()
	{

	}

	/**
	 * Gets the sensitivity of the mouse.
	 * @return {@link Vector2} - The sensitivity of the mouse
	 */
	public Vector2 sensitivity()
	{
		return sensitivity;
	}

	/**
	 * Sets this instance to another {@link GameConfig}.
	 * @param config The config
	 */
	public void set(GameConfig config)
	{
		if (config == null) return;
		setTitle(config.title());
		setWidth(config.width());
		setHeight(config.height());
		setX(config.x());
		setY(config.y());
		setMaxFps(config.maxFps());
		setStep(config.step());
		setClamp(config.clamp());
		setBackgroundFps(config.backgroundFps());
		setVsync(config.isVsync());
		setResizable(config.isResizable());
		setFullscreen(config.allowsFullscreen());
		setFullscreenStartup(config.startsFullscreen());
		setStatusBar(config.hasStatusBar());
		setImmersiveMode(config.isImmersive());
		setIcon(config.icon());
		setAntiAliasing(config.antiAliasing());
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

	public void setGL30(boolean gl30)
	{
		this.gl30 = gl30;
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
		sensitivity().set(sensitivity, sensitivity);
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

	public void setWindowLocation(int x, int y)
	{
		setX(x);
		setY(y);
	}

	public void setWindowSize(int width, int height)
	{
		setWidth(width);
		setHeight(height);
	}

	/**
	 * Sets the starting x location of the window.
	 * @param x The x location of the window
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * Sets the starting y location of the window.
	 * @param y The y location of the window
	 */
	public void setY(int y)
	{
		this.y = y;
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
	 * Gets the number of logic updates per second in the {@link Game}.
	 * @return {@link int} - The number of logic updates per second in the
	 * {@link Game}
	 */
	public int step()
	{
		return step;
	}

	/**
	 * Gets the interval between {@link Game} logic updates in milliseconds.
	 * @return {@link float} - The interval between {@link Game} logic updates
	 * in milliseconds
	 */
	public float stepInterval()
	{
		return new Reciprocal(step).floatValue();
	}

	/**
	 * Gets the title of the {@link Game} window.
	 * @return {@link String} - The title of the {@link Game} window
	 */
	public String title()
	{
		return title;
	}

	/**
	 * Gets the {@link AndroidApplicationConfiguration} of the
	 * {@link GameConfig}.
	 * @return {@link AndroidApplicationConfiguration} - The config.
	 */
	@SuppressWarnings("deprecation")
	public AndroidApplicationConfiguration toAndroidConfig()
	{
		AndroidApplicationConfiguration config
				= new AndroidApplicationConfiguration();
		config.hideStatusBar = hasStatusBar();
		config.useImmersiveMode = isImmersive();
		config.numSamples = antiAliasing();
		config.useGL30 = usesGL30();
		return config;
	}

	/**
	 * Gets the {@link Lwjgl3ApplicationConfiguration} of the
	 * {@link GameConfig}.
	 * @return {@link Lwjgl3ApplicationConfiguration} - The config.
	 */
	public Lwjgl3ApplicationConfiguration toLwjgl3Config()
	{
		Lwjgl3ApplicationConfiguration config
				= new Lwjgl3ApplicationConfiguration();
		config.setTitle(title());
		config.setWindowedMode(width(), height());
		config.useVsync(isVsync());
		config.setResizable(isResizable());
		config.setWindowPosition(x(), y());
		config.useOpenGL3(usesGL30(), 3, 2);
		// config.foregroundFPS = getMaxFps();
		// config.backgroundFPS = getBackgroundFps();
		// config.samples = getAntiAliasing();
		// if (getIcon().exists()) config.addIcon(getIcon().path(), ICON_TYPE);
		return config;
	}

	/**
	 * Gets the {@link LwjglApplicationConfiguration} of the {@link GameConfig}.
	 * @return {@link LwjglApplicationConfiguration} - The config.
	 */
	public LwjglApplicationConfiguration toLwjglConfig()
	{
		LwjglApplicationConfiguration config
				= new LwjglApplicationConfiguration();
		config.title = title();
		config.width = width();
		config.height = height();
		config.vSyncEnabled = isVsync();
		config.foregroundFPS = maxFps();
		config.backgroundFPS = backgroundFps();
		config.resizable = isResizable();
		config.samples = antiAliasing();
		config.x = x();
		config.y = y();
		config.useGL30 = usesGL30();
		if (icon().exists()) config.addIcon(icon().path(), ICON_TYPE);
		return config;
	}

	public boolean usesGL30()
	{
		return gl30;
	}

	/**
	 * Gets the starting width of the window in pixels.
	 * @return {@link int} - The starting width of the window in pixels
	 */
	public int width()
	{
		return width;
	}

	/**
	 * Gets the starting x location of the window.
	 * @return {@link int} - The starting x location of the window
	 */
	public int x()
	{
		return x;
	}

	/**
	 * Gets the starting y location of the window.
	 * @return {@link int} - The starting y location of the window
	 */
	public int y()
	{
		return y;
	}
}
