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

	public static FileHandle getAbsoluteFileHandle(String path)
	{
		return getFiles().absolute(path);
	}

	private static Files getFiles()
	{
		return Gdx.files;
	}

	public static FileHandle getInternalFileHandle(String path)
	{
		return getFiles().internal(path);
	}

	public static FileHandle getLocalFileHandle(String path)
	{
		return getFiles().local(path);
	}

	public static String read(String location)
	{
		return getAbsoluteFileHandle(location).readString();
	}

	public static void write(String location, String contents)
	{
		write(location, contents, true);
	}

	public static void write(String location, String contents, boolean overwrite)
	{
		getLocalFileHandle(location).writeString(contents, !overwrite);
	}
}
