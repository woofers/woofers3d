package com.jaxson.lib.gdx.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.jaxson.lib.util.File;

import java.nio.ByteBuffer;

public class Screenshot extends GdxSaveObject implements Disposable
{
	private static final String SCREENSHOT_NAME = "Screenshot ";
	private static final String SCREENSHOT_FOLDER = "screenshots";
	private static final String SCREENSHOT_EXTENSION = ".png";

	private Pixmap image;

	public Screenshot()
	{
		this(getScreenshotFile());
	}

	public Screenshot(GdxFile file)
	{
		setSaveFile(file);
		this.image = getScreenshot(getGraphics().getWidth(), getGraphics().getHeight());
	}

	public Screenshot(String path)
	{
		this(new GdxFile(path));
	}

	@Override
	public void dispose()
	{
		try
		{
			image.dispose();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		image = null;
	}

	@Override
	public void save()
	{
		GdxFile file = (GdxFile) getSaveFile();
		file.write(image);
	}

	private static Pixmap flipPixmap(Pixmap pixmap, int width, int height)
	{
		ByteBuffer pixels = pixmap.getPixels();
		int numBytes = width * height * 4;
		byte[] lines = new byte[numBytes];
		int numBytesPerLine = width * 4;
		for (int i = 0; i < height; i ++)
		{
			pixels.position((height - i - 1) * numBytesPerLine);
			pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
		}
		pixels.clear();
		pixels.put(lines);
		pixels.clear();
		return pixmap;
	}

	private static Graphics getGraphics()
	{
		return Gdx.graphics;
	}

	public static Pixmap getScreenshot(int width, int height)
	{
		return getScreenshot(0, 0, width, height, true);
	}

	public static Pixmap getScreenshot(int x, int y, int width, int height, boolean yDown)
	{
		Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(x, y, width, height);
		if (yDown) pixmap = flipPixmap(pixmap, width, height);
		return pixmap;
	}

	private static GdxFile getScreenshotFile()
	{
		int counter = 0;
		GdxFile file;
		do
		{
			counter ++;
			file = new GdxFile(getScreenshotPath(counter));
		}
		while (file.exists());
		return file;
	}

	private static String getScreenshotPath(int index)
	{
		return SCREENSHOT_FOLDER + File.FOWARD_SLASH + SCREENSHOT_NAME + index + SCREENSHOT_EXTENSION;
	}
}
