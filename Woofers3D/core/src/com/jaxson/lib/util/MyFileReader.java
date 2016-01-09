package com.jaxson.lib.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * A FileReader that handles writing and reading.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class MyFileReader
{
	/**
	 * Gets whether the file exists.
	 * @param path The path of the file
	 * @return {@code boolean} - Whether the object exists
	 */
	public static boolean exists(String path)
	{
		return new File(path).exists();
	}

	/**
	 * Parses a text file
	 * @param path The path of the file
	 * @return {@code String} - The contents of the file
	 */
	public static String read(String path)
	{
		BufferedReader reader = null;
		String output = "";
		try
		{
			reader = new BufferedReader(new FileReader(path));
			String nextLine = "";
			while (nextLine != null)
			{
				output += reader.readLine();
				output += System.lineSeparator();
				nextLine = reader.readLine();
			}
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
