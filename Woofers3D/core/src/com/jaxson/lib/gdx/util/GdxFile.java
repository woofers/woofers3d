package com.jaxson.lib.gdx.util;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.UBJsonReader;
import com.jaxson.lib.util.File;

public class GdxFile extends File
{
	private static final String G3DJ_EXTENSION = "g3dj";
	private static final String G3DB_EXTENSION = "g3db";
	private static final String OBJ_EXTENSION = "obj";
	private static final String LOADER_NOT_FOUND = "Loader could not be found for given filetype.";

	private FileHandle fileHandle;

	public GdxFile(String path)
	{
		this(path, FileType.Absolute);
	}

	public GdxFile(String path, FileType fileType)
	{
		super(path);
		this.fileHandle = getFileHandle(fileType);
	}

	public void add(String contents)
	{
		write(contents, false);
	}

	@Override
	public boolean exists()
	{
		return fileHandle.exists();
	}

	public FileHandle getAbsoluteFile()
	{
		return getAbsoluteFile(getPath());
	}

	public FileHandle getClasspathFile()
	{
		return getClasspathFile(getPath());
	}

	public FileHandle getExternalFile()
	{
		return getExternalFile(getPath());
	}

	public FileHandle getFileHandle()
	{
		return fileHandle;
	}

	private FileHandle getFileHandle(FileType fileType)
	{
		switch (fileType)
		{
			case Absolute:
				return getAbsoluteFile();
			case Classpath:
				return getClasspathFile();
			case External:
				return getExternalFile();
			case Internal:
				return getInternalFile();
		}
		return getLocalFile();
	}

	public FileHandle getInternalFile()
	{
		return getInternalFile(getPath());

	}

	public FileHandle getLocalFile()
	{
		return getLocalFile(getPath());
	}

	public Model loadG3db()
	{
		return new G3dModelLoader(new UBJsonReader()).loadModel(fileHandle);
	}

	public Model loadG3dj()
	{
		return new G3dModelLoader(new JsonReader()).loadModel(fileHandle);
	}

	public Model loadModel()
	{
		String extension = getExtension();
		if (extension.equals(G3DB_EXTENSION)) return loadG3db();
		if (extension.equals(G3DJ_EXTENSION)) return loadG3dj();
		if (extension.equals(OBJ_EXTENSION)) return loadObj();
		throw new IllegalArgumentException(LOADER_NOT_FOUND);
	}

	public Model loadObj()
	{
		return new ObjLoader().loadModel(fileHandle);
	}

	@Override
	public String read()
	{
		return getFileHandle().readString();
	}

	public void write(Pixmap pixmap)
	{
		PixmapIO.writePNG(getFileHandle(), pixmap);
	}

	@Override
	public void write(String contents)
	{
		write(contents, true);
	}

	public void write(String contents, boolean overwrite)
	{
		getFileHandle().writeString(contents, !overwrite);
	}

	public static FileHandle getAbsoluteFile(String path)
	{
		return getFiles().absolute(path);
	}

	public static FileHandle getClasspathFile(String path)
	{
		return getFiles().classpath(path);
	}

	public static FileHandle getExternalFile(String path)
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

	/*
	 * public static Pixmap getScreenshot(int width, int height)
	 * {
	 * return getScreenshot(0, 0, width, height);
	 * }
	 * public static Pixmap getScreenshot(int x, int y, int width, int height)
	 * {
	 * return getScreenshot(x, y, width, height, true);
	 * }
	 * public static Pixmap getScreenshot(int x, int y, int width, int height,
	 * boolean yDown)
	 * {
	 * Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(x, y, width, height);
	 * if (yDown) pixmap = flipPixmap(pixmap, width, height);
	 * return pixmap;
	 * }
	 * public static GdxSprite getScreenshotSprite(int width, int height)
	 * {
	 * return new GdxSprite(getScreenshotTexture(width, height));
	 * }
	 * private static Texture getScreenshotTexture(int width, int height)
	 * {
	 * return new Texture(getScreenshotTextureData(width, height));
	 * }
	 * private static TextureData getScreenshotTextureData(int width, int
	 * height)
	 * {
	 * Pixmap pixmap = getScreenshot(width, height);
	 * return new PixmapTextureData(pixmap, pixmap.getFormat(), true, true);
	 * }
	 */
}
