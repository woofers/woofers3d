package com.jaxson.lib.gdx.util;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.UBJsonReader;
import com.jaxson.lib.util.MyFileReader;

public class GdxFileReader extends MyFileReader
{
	private static final String G3DJ_EXTENSION = "g3dj";
	private static final String G3DB_EXTENSION = "g3db";
	private static final String OBJ_EXTENSION = "obj";
	private static final String EXCEPTION_MESSAGE = "Loader could not be found for given filetype.";

	public static void add(String location, String contents)
	{
		write(location, contents, false);
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

	public static FileHandle getInternalFile(String path)
	{
		return getFiles().internal(path);
	}

	public static FileHandle getLocalFile(String path)
	{
		return getFiles().local(path);
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

	public static void write(String path, String contents)
	{
		write(path, contents, true);
	}

	public static void write(String location, String contents, boolean overwrite)
	{
		getLocalFile(location).writeString(contents, !overwrite);
	}
}
