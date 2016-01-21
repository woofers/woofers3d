package com.jaxson.lib.gdx.util;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.UBJsonReader;
import com.jaxson.lib.gdx.graphics.g2d.GdxSprite;
import com.jaxson.lib.util.MyFileReader;

import java.nio.ByteBuffer;

public class GdxFileReader extends MyFileReader
{
	private static final String G3DJ_EXTENSION = "g3dj";
	private static final String G3DB_EXTENSION = "g3db";
	private static final String OBJ_EXTENSION = "obj";
	private static final String EXCEPTION_MESSAGE = "Loader could not be found for given filetype.";
	private static final String SCREENSHOT_NAME = "Screenshot ";
	private static final String SCREENSHOT_FOLDER = "screenshots";
	private static final String SCREENSHOT_EXTENSION = ".png";

	public static void add(String location, String contents)
	{
		write(location, contents, false);
	}

	public static boolean exists(String path)
	{
		return getLocalFile(path).exists();
	}

	public static FileHandle getAbsoluteFile(String path)
	{
		return getFiles().absolute(path);
	}

	public static FileHandle getClasspathFile(String path)
	{
		return getFiles().classpath(path);
	}

	public static FileHandle getExternal(String path)
	{
		return getFiles().external(path);
	}

	private static Files getFiles()
	{
		return Gdx.files;
	}

	private static Graphics getGraphics()
	{
		return Gdx.graphics;
	}

	public static FileHandle getInternalFile(String path)
	{
		return getFiles().internal(path);
	}

	public static FileHandle getLocalFile(String path)
	{
		return getFiles().local(path);
	}

	public static Pixmap getScreenshot(int width, int height)
	{
		return getScreenshot(0, 0, width, height);
	}

	public static Pixmap getScreenshot(int x, int y, int width, int height)
	{
		return getScreenshot(x, y, width, height, true);
	}

	public static Pixmap getScreenshot(int x, int y, int width, int height, boolean yDown)
	{
		Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(x, y, width, height);
		if (yDown)
		{
			ByteBuffer pixels = pixmap.getPixels();
			int numBytes = width * height * 4;
			byte[] lines = new byte[numBytes];
			int numBytesPerLine = width * 4;
			for (int i = 0; i < height; i++)
			{
				pixels.position((height - i - 1) * numBytesPerLine);
				pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
			}
			pixels.clear();
			pixels.put(lines);
			pixels.clear();
		}
		return pixmap;
	}

	public static GdxSprite getScreenshotSprite(int width, int height)
	{
		return new GdxSprite(getScreenshotTexture(width, height));
	}

	public static Texture getScreenshotTexture(int width, int height)
	{
		return new Texture(getScreenshotTextureData(width, height));
	}

	public static TextureData getScreenshotTextureData(int width, int height)
	{
		Pixmap pixmap = getScreenshot(width, height);
		return new PixmapTextureData(pixmap, pixmap.getFormat(), true, true);
	}

	public static Model loadG3db(String path)
	{
		return new G3dModelLoader(new UBJsonReader()).loadModel(getInternalFile(path));
	}

	public static Model loadG3dj(String path)
	{
		return new G3dModelLoader(new JsonReader()).loadModel(getInternalFile(path));
	}

	public static Model loadModel(String path)
	{
		String extension = getFileExtension(path);
		if (extension.equals(G3DB_EXTENSION)) return loadG3db(path);
		if (extension.equals(G3DJ_EXTENSION)) return loadG3dj(path);
		if (extension.equals(OBJ_EXTENSION)) return loadObj(path);
		throw new IllegalArgumentException(EXCEPTION_MESSAGE);
	}

	public static Model loadObj(String path)
	{
		return new ObjLoader().loadModel(getInternalFile(path));
	}

	public static String read(String path)
	{
		return getAbsoluteFile(path).readString();
	}

	public static void saveScreenshot()
	{
		FileHandle file;
		int counter = 0;
		do
		{
			counter ++;
			file = new FileHandle(SCREENSHOT_FOLDER + FOWARD_SLASH + SCREENSHOT_NAME + counter + SCREENSHOT_EXTENSION);
		}
		while (file.exists());
		saveScreenshot(file);
	}

	public static void saveScreenshot(FileHandle file)
	{
		try
		{
			Pixmap pixmap = getScreenshot(getGraphics().getWidth(), getGraphics().getHeight());
			PixmapIO.writePNG(file, pixmap);
			pixmap.dispose();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public static void saveScreenshot(String path)
	{
		saveScreenshot(getLocalFile(path));
	}

	public static void write(String path, String contents)
	{
		write(path, contents, true);
	}

	public static void write(String location, String contents, boolean overwrite)
	{
		getLocalFile(location).writeString(contents, !overwrite);
	}
}
