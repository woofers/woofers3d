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

	private GdxFile file;
	private Pixmap image;

	public Screenshot()
	{
		saveScreenshot();
		this.file = screenshotFile();
	}

	@Override
	public void dispose()
	{
		image.dispose();
	}

	public Screenshot flipY()
	{
		ByteBuffer pixels = pixmap().getPixels();
		int bytesPerLine = width() * BYTES_PER_PIXEL;
		int totalBytes = area() * BYTES_PER_PIXEL;
		byte[] lines = new byte[totalBytes];
		for (int i = 0; i < height(); i ++)
		{
			pixels.position((height() - i - 1) * bytesPerLine);
			pixels.get(lines, i * bytesPerLine, bytesPerLine);
		}
		pixels.clear();
		pixels.put(lines);
		pixels.clear();
		return this;
	}

	public int area()
	{
		return width() * height();
	}

	public int height()
	{
		return pixmap().getHeight();
	}

	public Pixmap pixmap()
	{
		return image;
	}

	public int width()
	{
		return pixmap().getWidth();
	}

	public Screenshot save()
	{
		file.write(this);
		return this;
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
		return new PixmapTextureData(pixmap(),
									 pixmap().getFormat(),
									 true, true);
	}

	private void saveScreenshot()
	{
		saveScreenshot(graphics().getWidth(), graphics().getHeight());
	}

	private void saveScreenshot(int width, int height)
	{
		saveScreenshot(0, 0, width, height, true);
	}

	private void saveScreenshot(int x, int y, int width, int height,
								boolean yDown)
	{
		this.image = ScreenUtils.getFrameBufferPixmap(x, y, width, height);
		if (yDown) flipY();
	}

	private static Graphics graphics()
	{
		return Gdx.graphics;
	}

	private static GdxFile screenshotFile()
	{
		int counter = 0;
		GdxFile file;
		do
		{
			counter ++;
			file = new GdxFile(screenshotPath(counter));
		}
		while (file.exists());
		return file;
	}

	private static String screenshotPath(int index)
	{
		return FOLDER + File.FOWARD_SLASH + NAME + index + EXTENSION;
	}
}
