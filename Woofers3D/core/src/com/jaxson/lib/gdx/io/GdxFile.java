package com.jaxson.lib.gdx.io;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.UBJsonReader;
import com.jaxson.lib.io.File;

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
		setFileType(fileType);
	}

	public void add(String contents)
	{
		write(contents, false);
	}

	@Override
	public boolean exists()
	{
		return getFileHandle().exists();
	}

	private FileHandle getAbsoluteFile()
	{
		return getFiles().absolute(getPath());
	}

	private FileHandle getClasspathFile()
	{
		return getFiles().classpath(getPath());
	}

	private FileHandle getExternalFile()
	{
		return getFiles().external(getPath());
	}

	public FileHandle getFileHandle()
	{
		return fileHandle;
	}

	public FileType getType()
	{
		return getFileHandle().type();
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

	private FileHandle getInternalFile()
	{
		return getFiles().internal(getPath());
	}

	private FileHandle getLocalFile()
	{
		return getFiles().local(getPath());
	}

	public void setFileType(FileType fileType)
	{
		this.fileHandle = getFileHandle(fileType);
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

	private static Files getFiles()
	{
		return Gdx.files;
	}
}
