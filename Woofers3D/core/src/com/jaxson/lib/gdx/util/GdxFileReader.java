package com.jaxson.lib.gdx.util;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class GdxFileReader
{
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

	public static String read(String location)
	{
		return getAbsoluteFile(location).readString();
	}

	public static void write(String location, String contents)
	{
		write(location, contents, true);
	}

	public static void write(String location, String contents, boolean overwrite)
	{
		getLocalFile(location).writeString(contents, !overwrite);
	}
}
