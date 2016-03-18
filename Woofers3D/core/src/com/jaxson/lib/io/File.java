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
	public static final String NO_EXTENSION = "";
	public static final String DOT = Pattern.quote(".");
	public static final String NEXT_LINE = System.lineSeparator();

	private String path;

	/**
	 * Constructs a {@link File} from a {@link String}.
	 * @param path The path
	 */
	public File(String path)
	{
		setPath(path);
	}

	public void add(String contents)
	{
		write(readString() + contents);
	}

	public File createFile()
	{
		if (exists())
		{
			if (isFile()) return this;
			delete();
		}
		write("");
		return this;
	}

	public File createDirectory()
	{
		if (exists())
		{
			if (isDirectory()) return this;
			delete();
		}
		try
		{
			getJavaFile().mkdirs();
		}
		catch (SecurityException ex)
		{
			ex.printStackTrace();
		}
		return this;
	}

	public File delete()
	{
		if (!exists()) return this;
		try
		{
			getJavaFile().delete();
		}
		catch (SecurityException ex)
		{
			ex.printStackTrace();
		}
		return this;
	}

	public File copy(String path)
	{
		return copy(new File(path));
	}

	public File copy(File file)
	{
		if (equals(file)) return this;
		file.write(readBytes());
		return file;
	}

	public boolean equals(Object file)
	{
		if (file instanceof File) return equals((File)file);
		return false;
	}

	public boolean equals(File file)
	{
		return equals(file.getPath());
	}

	public boolean equals(String path)
	{
		return getPath().equals(path);
	}

	/**
	 * Gets whether the {@link File} exists.
	 * @return {@link boolean} - Whether the {@link File} exists
	 */
	public boolean exists()
	{
		return getJavaFile().exists();
	}

	/**
	 * Return a {@link BufferedReader} from the {@link File}
	 * @return {@link BufferedReader} - The buffered reader
	 * @throws FileNotFoundException If the file is not found
	 */

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

	public String getParentPath()
	{
		int index = getPath().lastIndexOf(FOWARD_SLASH);
		if (index == -1) return NO_EXTENSION;
		return getPath().substring(0, index + 1);
	}

	public PrintWriter getPrintWriter() throws FileNotFoundException, UnsupportedEncodingException
	{
		return new PrintWriter(getPath());
	}

	/**
	 * Gets whether the {@link File} is a directory.
	 * @return {@link boolean} - Whether the {@link File} is a directory
	 */
	public boolean isDirectory()
	{
		return getJavaFile().isDirectory();
	}

	/**
	 * Gets whether the {@link File} is a file.
	 * @return {@link boolean} - Whether the {@link File} is a file
	 */
	public boolean isFile()
	{
		return !isDirectory();
	}

	/**
	 * Gets the size of the {@link File} in {@link byte}s.
	 * @return {@link long} - The size in {@link byte}s
	 */
	public long length()
	{
		return getJavaFile().length();
	}

	/**
	 * Parses a the {@link File} as a {@link byte} array.
	 * @return {@link byte[]} - The contents of the file as a {@link byte} array
	 */
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
	 * Parses a the {@link File} as a {@link String}.
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
				output += NEXT_LINE;
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

	public File rename(String path)
	{
		return rename(new File(path));
	}

	public File rename(File file)
	{
		if (equals(file)) return this;
		if (getJavaFile().renameTo(file.getJavaFile())) return file;
		return this;
	}

	private void setPath(String path)
	{
		this.path = path.replace(BACK_SLASH, FOWARD_SLASH);
	}

	/**
	 * Writes to the {@link File}.
	 * @param contents The contents to write as a {@link String}
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

	/**
	 * Writes to the {@link File}.
	 * @param contents The contents to write as {@link byte}s
	 */
	public void write(byte[] contents)
	{
		FileOutputStream stream = null;
		try
		{
			stream = getFileOutputStream();
			stream.write(contents);
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
	}

	public File getParent()
	{
		return new File(getParentPath());
	}
}
