package com.jaxson.lib.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.regex.Pattern;

/**
 * A FileReader that handles writing and reading.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class MyFileReader
{
	protected static final String FOWARD_SLASH = "/";

	/**
	 * Gets whether the file exists.
	 * @param path The path of the file
	 * @return {@link boolean} - Whether the object exists
	 */
	public static boolean exists(String path)
	{
		return new File(path).exists();
	}

	/**
	 * Gets the file extension of a path. Returns null if the file has no
	 * extesnion.
	 * @param path The path of the file
	 * @return {@link String} - The file extension
	 */
	public static String getFileExtension(String path)
	{
		String dot = Pattern.quote(".");
		String[] fileNames = getFileName(path).split(dot);
		if (fileNames.length == 1)
		{
			if (fileNames[0].contains(dot)) return fileNames[0];
			return null;
		}
		return fileNames[1];
	}

	/**
	 * Gets the file name of a path.
	 * @param path The path of the file
	 * @return {@link String} - The file name
	 */
	public static String getFileName(String path)
	{
		String[] directories = path.split(FOWARD_SLASH);
		return directories[directories.length - 1];
	}

	/**
	 * Parses a text file
	 * @param path The path of the file
	 * @return {@link String} - The contents of the file
	 */
	public static String read(String path)
	{
		BufferedReader reader = null;
		String output = "";
		try
		{
			reader = new BufferedReader(new FileReader(path));
			String nextLine = "";
			do
			{
				output += nextLine;
				output += System.lineSeparator();
				nextLine = reader.readLine();
			} while (nextLine != null);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return output;
	}

	/**
	 * Writes to a text file
	 * @param path The path to write to
	 * @param contents The contents to write
	 */
	public static void write(String path, String contents)
	{
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(path);
			writer.print(contents);

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			writer.close();
		}
	}
}
