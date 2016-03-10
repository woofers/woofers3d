package com.jaxson.lib.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

/**
 * A File that handles writing and reading.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class File
{
	public static final String FOWARD_SLASH = "/";
	public static final String BACK_SLASH = "\\";
	public static final String DOT = Pattern.quote(".");
	public static final String NEW_LINE = System.lineSeparator();

	private String path;

	public File(String path)
	{
		setPath(path);
	}

	/**
	 * Gets whether the {@link File} exists.
	 * @return {@link boolean} - Whether the {@link File} exists
	 */
	public boolean exists()
	{
		return getJavaFile().exists();
	}

	public BufferedReader getBufferedReader() throws FileNotFoundException
	{
		FileReader fileReader = null;
		try
		{
			fileReader = getFileReader();
		}
		catch (FileNotFoundException ex)
		{
			throw ex;
		}
		return new BufferedReader(fileReader);
	}

	/**
	 * Gets the file extension of the {@link File}. Returns an empty string if
	 * the {@link File} has no extension.
	 * @return {@link String} - The file extension
	 */
	public String getExtension()
	{
		String[] fileNames = getName().split(DOT);
		if (fileNames.length == 1) return "";
		return fileNames[1];
	}

	public FileType getExtensionType()
	{
		return FileType.getType(getExtension());
	}

	public FileInputStream getFileInputStream() throws FileNotFoundException
	{
		return new FileInputStream(getJavaFile());
	}

	public FileOutputStream getFileOutputStream() throws FileNotFoundException, SecurityException
	{
		return new FileOutputStream(getPath());
	}

	public FileReader getFileReader() throws FileNotFoundException
	{
		return new FileReader(getPath());
	}

	/**
	 * Gets the {@link java.io.File} of the {@link File}.
	 * @return {@link java.io.File} - The file
	 */
	public java.io.File getJavaFile()
	{
		return new java.io.File(getPath());
	}

	/**
	 * Gets the file name of the {@link File}.
	 * @return {@link String} - The file name
	 */
	public String getName()
	{
		int index = getPath().lastIndexOf(FOWARD_SLASH);
		return getPath().substring(index + 1);
	}

	/**
	 * Gets the file path of the {@link File}.
	 * @return {@link String} - The file path
	 */
	public String getPath()
	{
		return path;
	}

	public String getPathWithoutName()
	{
		int index = getPath().lastIndexOf(FOWARD_SLASH);
		if (index == -1) return "";
		return getPath().substring(0, index + 1);
	}

	public PrintWriter getPrintWriter() throws FileNotFoundException, UnsupportedEncodingException
	{
		return new PrintWriter(getPath());
	}

	public boolean isDirectory()
	{
		return getJavaFile().isDirectory();
	}

	public boolean isFile()
	{
		return !isDirectory();
	}

	public long length()
	{
		return getJavaFile().length();
	}

	public byte[] readBytes()
	{
		FileInputStream stream = null;
		byte bytes[] = new byte[(int) length()];
		try
		{
			stream = getFileInputStream();
			stream.read(bytes);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				stream.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return bytes;
	}

	/**
	 * Parses a the {@link File}.
	 * @return {@link String} - The contents of the file
	 */
	public String readString()
	{
		BufferedReader reader = null;
		String output = "";
		try
		{
			reader = getBufferedReader();
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

	private void setPath(String path)
	{
		this.path = path.replace(BACK_SLASH, FOWARD_SLASH);
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
			writer = getPrintWriter();
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
