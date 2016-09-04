package com.jaxson.lib.gdx.graphics.g2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.jaxson.lib.gdx.io.GdxFile;
import com.jaxson.lib.io.File;
import java.nio.ByteBuffer;

public class Screenshot implements Disposable
{
	private static final String NAME = "Screenshot ";
	private static final String FOLDER = "screenshots";
	private static final String EXTENSION = ".png";
	private static final int BYTES_PER_PIXEL = 4;

	private static Graphics getGraphics()
	{
		return Gdx.graphics;
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
		return FOLDER + File.FOWARD_SLASH + NAME + index + EXTENSION;
	}

	private GdxFile file;
	private Pixmap image;

	public Screenshot()
	{
		saveScreenshot();
		this.file = getScreenshotFile();
	}

	@Override
	public void dispose()
	{
		image.dispose();
	}

	public void flipY()
	{
		ByteBuffer pixels = getPixmap().getPixels();
		int bytesPerLine = getWidth() * BYTES_PER_PIXEL;
		int totalBytes = getArea() * BYTES_PER_PIXEL;
		byte[] lines = new byte[totalBytes];
		for (int i = 0; i < getHeight(); i ++)
		{
			pixels.position((getHeight() - i - 1) * bytesPerLine);
			pixels.get(lines, i * bytesPerLine, bytesPerLine);
		}
		pixels.clear();
		pixels.put(lines);
		pixels.clear();
	}

	public int getArea()
	{
		return getWidth() * getHeight();
	}

	public int getHeight()
	{
		return getPixmap().getHeight();
	}

	public Pixmap getPixmap()
	{
		return image;
	}

	public int getWidth()
	{
		return getPixmap().getWidth();
	}

	public void save()
	{
		file.write(this);
	}

	private void saveScreenshot()
	{
		saveScreenshot(getGraphics().getWidth(), getGraphics().getHeight());
	}

	private void saveScreenshot(int width, int height)
	{
		saveScreenshot(0, 0, width, height, true);
	}

	private void saveScreenshot(int x, int y, int width, int height, boolean yDown)
	{
		this.image = ScreenUtils.getFrameBufferPixmap(x, y, width, height);
		if (yDown) flipY();
	}

	public SpriteActor toSprite()
	{
		return new SpriteActor(toTexture());
	}

	public Texture toTexture()
	{
		return new Texture(toTextureData());
	}

	public TextureData toTextureData()
	{
		return new PixmapTextureData(getPixmap(), getPixmap().getFormat(), true, true);
	}
}
