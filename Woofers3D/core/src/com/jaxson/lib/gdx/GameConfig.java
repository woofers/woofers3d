package com.jaxson.lib.gdx;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.jaxson.lib.util.MyFileReader;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.jaxson.lib.util.GsonObject;

public class GameConfig extends GsonObject<GameConfig>
{
	private static final String TITLE = "New Game";
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS = 400;
	private static final int BACKGROUND_FPS = -1;
	private static final boolean VSYNC = false;
	private static final boolean RESIZABLE = false;
	private static final boolean ALLOW_FULLSCREEN = true;
	private static final boolean START_FULLSCREEN = false;
	private static final float STEP = 1f / 120f;
	private static final boolean STATUS_BAR = false;
	private static final boolean IMMERSIVE = true;
	private static final FileType ICON_TYPE = FileType.Internal;
	private static final String CONFIG_PATH = "config";
	private static final String CONFIG_TYPE = ".json";
	private static final boolean SHOW_FPS = true;

	public static final float CLAMP = 1f / 4f;
	public static final int CLEAR_MASK = GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT;
	public static final Color CLEAR_COLOR = Color.BLUE;

	private String title;
	private int width;
	private int height;
	private int fps;
	private transient int backgroundFps;
	private boolean vsync;
	private boolean resizable;
	private transient float step;
	private boolean allowFullscreen;
	private boolean startFullscreen;
	private boolean statusBar;
	private boolean immersive;
	private String iconPath;
	private transient String savePath;
	private boolean showFps;

	public GameConfig()
	{
		this(WIDTH, HEIGHT, FPS, STEP);
	}

	public GameConfig(int width, int height, int fps, float step)
	{
		super(GameConfig.class);
		setWidth(width);
		setHeight(height);
		setFps(fps);
		setStep(step);
		setTitle(TITLE);
		setBackgroundFps(BACKGROUND_FPS);
		setVsync(VSYNC);
		setResizable(RESIZABLE);
		setFullscreen(ALLOW_FULLSCREEN);
		setFullscreenStartup(START_FULLSCREEN);
		setStatusBar(STATUS_BAR);
		setImmersiveMode(IMMERSIVE);
		setSavePath(CONFIG_PATH);
		setShowFps(SHOW_FPS);
	}

	public boolean allowsFullscreen()
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

	public String getIconPath()
	{
		return iconPath;
	}

	public String getSaveLocation()
	{
		return savePath + CONFIG_TYPE;
	}

	public String getSavePath()
	{
		return savePath;
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

	public boolean hasIcon()
	{
		return getIconPath() != null;
	}

	public boolean hasStatusBar()
	{
		return statusBar;
	}

	public boolean isImmersive()
	{
		return immersive;
	}

	public boolean isResizable()
	{
		return resizable;
	}

	public boolean isVsync()
	{
		return vsync;
	}

	public void read()
	{
		String json = MyFileReader.read(getSaveLocation());
		set(fromJson(json));
	}

	public void readOrCreate()
	{
		if (MyFileReader.exists(getSaveLocation()))
		{
			read();
			return;
		}
		save();
	}

	public void save()
	{
		MyFileReader.write(getSaveLocation(), toJson());
	}

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

	public void setFullscreenStartup(boolean startFullscreen)
	{
		this.startFullscreen = startFullscreen;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public void setIconPath(String iconPath)
	{
		this.iconPath = iconPath;
	}

	public void setImmersiveMode(boolean immersive)
	{
		this.immersive = immersive;
	}

	public void setResizable(boolean resizable)
	{
		this.resizable = resizable;
	}

	public void setSavePath(String savePath)
	{
		this.savePath = savePath;
	}

	public void setShowFps(boolean showFps)
	{
		this.showFps = showFps;
	}

	public void setStatusBar(boolean statusBar)
	{
		this.statusBar = statusBar;
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

	public boolean showsFps()
	{
		return showFps;
	}

	public boolean startsFullscreen()
	{
		return startFullscreen;
	}

	public AndroidApplicationConfiguration toAndroidConfig()
	{
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.hideStatusBar = hasStatusBar();
		config.useImmersiveMode = isImmersive();
		return config;
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
		if (hasIcon()) config.addIcon(getIconPath(), ICON_TYPE);
		return config;
	}
}
