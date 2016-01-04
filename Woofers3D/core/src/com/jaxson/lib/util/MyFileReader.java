package com.jaxson.lib.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class MyFileReader
{
	public static boolean exists(String path)
	{
		return new File(path).exists();
	}

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
