package com.jaxson.lib.gdx;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.util.GsonObject;
import com.jaxson.lib.util.MyFileReader;

/**
 * A universal config file that can be converted to other config types.
 * Such as {@link AndroidApplicationConfiguration} or
 * {@link LwjglApplicationConfiguration}.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class GameConfig extends GsonObject<GameConfig>
{
	/**
	 * The minimum step that the {@link Game} cannot skip.
	 */
	public static final float CLAMP = 1f / 4f;

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS = 600;
	private static final float STEP = 1f / 120f;
	private static final FileType ICON_TYPE = FileType.Internal;
	private static final String CONFIG_PATH = "config.json";
	private static final float SENSITIVITY = 1.3f;

	private String title = "New Game";
	private int width;
	private int height;
	private int fps;
	private transient int backgroundFps = -1;
	private boolean vsync = false;
	private boolean resizable = false;
	private transient float step;
	private boolean allowFullscreen = true;
	private boolean startFullscreen = false;
	private int antiAliasing = 4;
	private boolean statusBar = false;
	private boolean immersive = true;
	private String iconPath;
	private transient String savePath = CONFIG_PATH;
	private boolean showFps = true;
	private Vector2 sensitivity = new Vector2(SENSITIVITY, SENSITIVITY);
	private boolean invertMouseX = true;
	private boolean invertMouseY = true;

	/**
	 * Constructs a default config.
	 */
	public GameConfig()
	{
		this(WIDTH, HEIGHT, FPS, STEP);
	}

	/**
	 * Constructs from another config.
	 */
	public GameConfig(GameConfig config)
	{
		super(GameConfig.class);
		set(config);
	}

	/**
	 * Constructs a {@link GameConfig}
	 * @param width The width of the window
	 * @param height The height of the window
	 * @param fps The target fps of the {@link Game}
	 * @param step The interval between {@link Game} logic updates in
	 * milliseconds
	 */
	public GameConfig(int width, int height, int fps, float step)
	{
		super(GameConfig.class);
		setWidth(width);
		setHeight(height);
		setFps(fps);
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
	 * @return {@link boolean} - The number of MSAA samples.
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
	 * Gets the target frame rate.
	 * @return {@link int} - The target frame rate
	 */
	public int getFps()
	{
		return fps;
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
	 * Gets the path of the {@link Game} icon.
	 * Returns {@code null} if the {@link Game} has no icon.
	 * @return {@link String} - The path of the {@link Game} icon
	 */
	public String getIconPath()
	{
		return iconPath;
	}

	/**
	 * Gets the path to save the {@link GameConfig} file.
	 * @return {@link String} - The path to save the {@link GameConfig} file
	 */
	public String getSavePath()
	{
		return savePath;
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
	 * Gets the interval between {@link Game} logic updates in milliseconds.
	 * @return {@link float} - The interval between {@link Game} logic updates
	 * in milliseconds
	 */
	public float getStep()
	{
		return step;
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
	 * Gets whether the {@link Game} has an icon.
	 * @return {@link boolean} - Whether the {@link Game} has an icon
	 */
	public boolean hasIcon()
	{
		return getIconPath() != null;
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
	 * @see {@link http://developer.android.com/training/system-ui/immersive.html}
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
	 * Reads the saved config file and sets the instance to it's values.
	 */
	public void read()
	{
		set(fromJson(MyFileReader.read(getSavePath())));
	}

	/**
	 * Searches for a config file and if one exists it reads it.
	 * Otherwise it creates one.
	 */
	public void readOrCreate()
	{
		if (MyFileReader.exists(getSavePath()))
		{
			read();
			return;
		}
		save();
	}

	/**
	 * Saves the current {@link GameConfig} to the {@link #getSavePath()}.
	 */
	public void save()
	{
		MyFileReader.write(getSavePath(), toJson());
	}

	/**
	 * Sets this instance to another {@link GameConfig}.
	 * @param config The config
	 */
	public void set(GameConfig config)
	{
		setTitle(config.getTitle());
		setWidth(config.getWidth());
		setHeight(config.getHeight());
		setFps(config.getFps());
		setStep(config.getStep());
		setBackgroundFps(config.getBackgroundFps());
		setVsync(config.isVsync());
		setResizable(config.isResizable());
		setFullscreen(config.allowsFullscreen());
		setFullscreenStartup(config.startsFullscreen());
		setStatusBar(config.hasStatusBar());
		setImmersiveMode(config.isImmersive());
		setSavePath(config.getSavePath());
		setIconPath(config.getIconPath());
		setShowFps(config.showsFps());
		setAntiAliasing(config.getAntiAliasing());
	}

	/**
	 * Sets the number of MSAA samples.
	 * Set to {@code 0} for MSAA to turn off.
	 * @param antiAliasing The number of MSAA samples
	 */
	public void setAntiAliasing(int antiAliasing)
	{
		this.antiAliasing = antiAliasing;
	}

	/**
	 * Sets the frame rate when the game is minimized.
	 * Set to {@code -1} for the game to pause on minimize.
	 * @param backgroundFps The frame rate when the game is minimized.
	 */
	public void setBackgroundFps(int backgroundFps)
	{
		this.backgroundFps = backgroundFps;
	}

	/**
	 * Sets the target frame rate.
	 * @param fps The target frame rate
	 */
	public void setFps(int fps)
	{
		this.fps = fps;
	}

	/**
	 * Sets whether the {@link Game} supports fullscreen.
	 * @param allowFullscreen Whether the {@link Game} supports fullscreen
	 */
	public void setFullscreen(boolean allowFullscreen)
	{
		this.allowFullscreen = allowFullscreen;
	}

	/**
	 * Sets whether the {@link Game} starts fullscreen.
	 * @param startFullscreen Whether the {@link Game} starts fullscreen
	 */
	public void setFullscreenStartup(boolean startFullscreen)
	{
		this.startFullscreen = startFullscreen;
	}

	/**
	 * Sets the starting height of the window in pixels.
	 * @param height The starting height of the window in pixels
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}

	/**
	 * Sets the path of the {@link Game} icon.
	 * Set to {@code null} to use the default icon.
	 * @param iconPath The path of the {@link Game} icon
	 */
	public void setIconPath(String iconPath)
	{
		this.iconPath = iconPath;
	}

	/**
	 * Sets whether the {@link Game} uses {@code Kitkat}'s immersive mode.
	 * @param immersive Whether the {@link Game} uses immersive mode.
	 * @see {@link http://developer.android.com/training/system-ui/immersive.html}
	 */
	public void setImmersiveMode(boolean immersive)
	{
		this.immersive = immersive;
	}

	/**
	 * Sets whether the {@link Game} window is resizable.
	 * @param resizable Whether the {@link Game} window is resizable
	 */
	public void setResizable(boolean resizable)
	{
		this.resizable = resizable;
	}

	/**
	 * Sets the path to save the {@link GameConfig} file.
	 * @param savePath The path to save the {@link GameConfig} file
	 */
	public void setSavePath(String savePath)
	{
		this.savePath = savePath;
	}

	/**
	 * Sets sensitivity of the mouse.
	 * @param sensitivity - The sensitivity of the mouse
	 */
	public void setSensitivity(float sensitivity)
	{
		getSensitivity().set(sensitivity, sensitivity);
	}

	/**
	 * Sets sensitivity of the mouse.
	 * Allows separate x and y sensitives.
	 * @param sensitivity - The sensitivity of the mouse
	 */
	public void setSensitivity(Vector2 sensitivity)
	{
		this.sensitivity = sensitivity;
	}

	/**
	 * Gets whether the {@link Game} shows its frame rate.
	 * @param showFps Whether the {@link Game} shows its frame rate
	 */
	public void setShowFps(boolean showFps)
	{
		this.showFps = showFps;
	}

	/**
	 * Sets whether the {@link Game} shows a status bar.
	 * @param statusBar Whether the {@link Game} shows a status bar
	 */
	public void setStatusBar(boolean statusBar)
	{
		this.statusBar = statusBar;
	}

	/**
	 * Sets the interval between {@link Game} logic updates in milliseconds.
	 * @param step The interval between {@link Game} logic updates in
	 * milliseconds
	 */
	public void setStep(float step)
	{
		this.step = step;
	}

	/**
	 * Sets the title of the {@link Game} window.
	 * @param title The title of the {@link Game} window
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Sets whether the {@link Game} uses vertical sync.
	 * @param vsync Whether the {@link Game} uses vertical sync
	 */
	public void setVsync(boolean vsync)
	{
		this.vsync = vsync;
	}

	/**
	 * Sets the starting width of the window in pixels.
	 * @param width The starting width of the window in pixels
	 */
	public void setWidth(int width)
	{
		this.width = width;
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
		config.foregroundFPS = getFps();
		config.backgroundFPS = getBackgroundFps();
		config.resizable = isResizable();
		config.samples = getAntiAliasing();
		if (hasIcon()) config.addIcon(getIconPath(), ICON_TYPE);
		return config;
	}
}
