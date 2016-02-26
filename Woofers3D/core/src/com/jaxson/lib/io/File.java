package com.jaxson.lib.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.regex.Pattern;

/**
 * A File that handles writing and reading.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class File
{
	public static final String FOWARD_SLASH = "/";
	public static final String DOT = Pattern.quote(".");
	public static final String NEW_LINE = System.lineSeparator();

	private String path;

	public File(String path)
	{
		this.path = path;
	}

	/**
	 * Gets whether the {@link File} exists.
	 * @return {@link boolean} - Whether the {@link File} exists
	 */
	public boolean exists()
	{
		return new java.io.File(getPath()).exists();
	}

	/**
	 * Gets the file extension of the {@link File}. Returns an empty string if
	 * the {@link File} has no extension.
	 * @return {@link String} - The file extension
	 */
	public String getExtension()
	{
		String[] fileNames = getName().split(DOT);
		if (fileNames.length == 1)
		{
			if (fileNames[0].contains(DOT)) return fileNames[0];
			return "";
		}
		return fileNames[1];
	}

	/**
	 * Gets the file name of the {@link File}.
	 * @return {@link String} - The file name
	 */
	public String getName()
	{
		String[] directories = getPath().split(FOWARD_SLASH);
		return directories[directories.length - 1];
	}

	/**
	 * Gets the file path of the {@link File}.
	 * @return {@link String} - The file path
	 */
	public String getPath()
	{
		return path;
	}

	/**
	 * Parses a the {@link File}.
	 * @return {@link String} - The contents of the file
	 */
	public String read()
	{
		BufferedReader reader = null;
		String output = "";
		try
		{
			reader = new BufferedReader(new FileReader(getPath()));
			String nextLine = "";
			do
			{
				output += nextLine;
				output += NEW_LINE;
				nextLine = reader.readLine();
			}
			while (nextLine != null);
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
	 * Writes to the {@link File}.
	 * @param contents The contents to write
	 */
	public void write(String contents)
	{
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(getPath());
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
