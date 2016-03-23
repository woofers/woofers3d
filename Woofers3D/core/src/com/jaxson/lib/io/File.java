package com.jaxson.lib.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;
import java.io.IOException;

/**
 * A File that handles writing and reading.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class File implements IFile<File>
{
	public static final String FOWARD_SLASH = "/";
	public static final String BACK_SLASH = "\\";
	public static final String NO_EXTENSION = "";
	public static final String DOT = Pattern.quote(".");
	public static final String NEXT_LINE = System.lineSeparator();

	private static final String PATH_EMPTY = "Path can not be empty";

	private String path;

	/**
	 * Constructs a {@link File} from a {@link String}.
	 * @param path The path
	 */
	public File(String path)
	{
		this.path = path;
		validatePath();
	}

	@Override
	public File append(String contents)
	{
		return write(readString() + contents);
	}

	@Override
	public File copy(File file)
	{
		if (equals(file)) return this;
		file.write(readBytes());
		return file;
	}

	@Override
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
		}
		return this;
	}

	@Override
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

	@Override
	public File delete()
	{
		if (!exists()) return this;
		try
		{
			getJavaFile().delete();
		}
		catch (SecurityException ex)
		{
			//throw new SecurityException(ex);
		}
		return this;
	}

	public boolean equals(File file)
	{
		return equals(file.getPath());
	}

	@Override
	public boolean equals(Object file)
	{
		if (file instanceof File) return equals((File) file);
		return false;
	}

	private boolean equals(String path)
	{
		return getPath().equals(path);
	}

	/**
	 * Gets whether the {@link File} exists.
	 * @return {@link boolean} - Whether the {@link File} exists
	 */
	@Override
	public boolean exists()
	{
		return getJavaFile().exists();
	}

	/**
	 * Return a {@link BufferedReader} from the {@link File}
	 * @return {@link BufferedReader} - The buffered reader
	 * @throws FileNotFoundException If the file is not found
	 */

	@Override
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

	public File getChild(String child)
	{
		if (child.charAt(0) != FOWARD_SLASH.charAt(0)) child = FOWARD_SLASH + child;
		return new File(getPath() + child);
	}

	/**
	 * Gets the file extension of the {@link File}. Returns an empty string if
	 * the {@link File} has no extension.
	 * @return {@link String} - The file extension
	 */
	@Override
	public String getExtension()
	{
		String[] fileNames = getName().split(DOT);
		if (fileNames.length == 1) return "";
		return fileNames[1];
	}

	@Override
	public FileType getExtensionType()
	{
		return new FileType(getExtension());
	}

	@Override
	public FileInputStream getFileInputStream() throws FileNotFoundException
	{
		return new FileInputStream(getJavaFile());
	}

	@Override
	public FileOutputStream getFileOutputStream() throws FileNotFoundException, SecurityException
	{
		return new FileOutputStream(getJavaFile());
	}

	@Override
	public FileReader getFileReader() throws FileNotFoundException
	{
		return new FileReader(getJavaFile());
	}

	/**
	 * Gets the {@link java.io.File} of the {@link File}.
	 * @return {@link java.io.File} - The file
	 */
	@Override
	public java.io.File getJavaFile()
	{
		return new java.io.File(getPath());
	}

	/**
	 * Gets the file name of the {@link File}.
	 * @return {@link String} - The file name
	 */
	@Override
	public String getName()
	{
		int index = getPath().lastIndexOf(FOWARD_SLASH);
		return getPath().substring(index + 1);
	}

	@Override
	public File getParent()
	{
		return new File(getParentPath());
	}

	@Override
	public String getParentPath()
	{
		int index = getPath().lastIndexOf(FOWARD_SLASH);
		if (index == -1) return NO_EXTENSION;
		return getPath().substring(0, index + 1);
	}

	/**
	 * Gets the file path of the {@link File}.
	 * @return {@link String} - The file path
	 */
	@Override
	public String getPath()
	{
		return path;
	}

	@Override
	public PrintWriter getPrintWriter() throws FileNotFoundException, UnsupportedEncodingException
	{
		return new PrintWriter(getJavaFile());
	}

	/**
	 * Gets whether the {@link File} is a directory.
	 * @return {@link boolean} - Whether the {@link File} is a directory
	 */
	@Override
	public boolean isDirectory()
	{
		return getJavaFile().isDirectory();
	}

	/**
	 * Gets whether the {@link File} is a file.
	 * @return {@link boolean} - Whether the {@link File} is a file
	 */
	@Override
	public boolean isFile()
	{
		return !isDirectory();
	}

	/**
	 * Gets the size of the {@link File} in {@link byte}s.
	 * @return {@link long} - The size in {@link byte}s
	 */
	@Override
	public long length()
	{
		return getJavaFile().length();
	}

	/**
	 * Parses a the {@link File} as a {@link byte} array.
	 * @return {@link byte[]} - The contents of the file as a {@link byte} array
	 */
	@Override
	public byte[] readBytes()
	{
		FileInputStream stream = null;
		byte bytes[] = new byte[(int) length()];
		try
		{
			stream = getFileInputStream();
			stream.read(bytes);
		}
		catch (FileNotFoundException ex)
		{
		}
		catch (SecurityException ex)
		{
		}
		catch (IOException ex)
		{
		}
		finally
		{
			try
			{
				if (stream != null) stream.close();
			}
			catch (IOException ex)
			{
			}
		}
		return bytes;
	}

	/**
	 * Parses a the {@link File} as a {@link String}.
	 * @return {@link String} - The contents of the file
	 */
	@Override
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
		catch (FileNotFoundException ex)
		{
		}
		catch (IOException ex)
		{
		}
		finally
		{
			try
			{
				if (reader != null) reader.close();
			}
			catch (IOException ex)
			{
			}
		}
		return output;
	}

	@Override
	public File rename(File file)
	{
		if (equals(file)) return this;
		if (getJavaFile().renameTo(file.getJavaFile())) return file;
		return this;
	}

	@Override
	public File rename(String name)
	{
		return rename(new File(name));
	}

	public File setPath(String path)
	{
		return new File(path);
	}

	private void validatePath()
	{
		path = path.replace(BACK_SLASH, FOWARD_SLASH).trim();
		String[] dirs = path.split(FOWARD_SLASH);
		String newDir = "";
		for (String dir: dirs)
		{
			dir = dir.trim();
			if (!dir.isEmpty()) newDir += dir + FOWARD_SLASH;
		}
		if (!newDir.isEmpty())
			path = newDir.substring(0, newDir.length() - 1);
		else
			throw new IllegalArgumentException(PATH_EMPTY);
	}

	/**
	 * Writes to the {@link File}.
	 * @param contents The contents to write as {@link byte}s
	 */
	@Override
	public File write(byte[] contents)
	{
		FileOutputStream stream = null;
		try
		{
			stream = getFileOutputStream();
			stream.write(contents);
		}
		catch (FileNotFoundException ex)
		{
		}
		catch (SecurityException ex)
		{
		}
		catch (IOException ex)
		{
		}
		finally
		{
			try
			{
				if (stream != null) stream.close();
			}
			catch (IOException ex)
			{
			}
		}
		return this;
	}

	/**
	 * Writes to the {@link File}.
	 * @param contents The contents to write as a {@link String}
	 */
	@Override
	public File write(String contents)
	{
		PrintWriter writer = null;
		try
		{
			writer = getPrintWriter();
			writer.print(contents);
		}
		catch (FileNotFoundException ex)
		{
		}
		catch (SecurityException ex)
		{
		}
		catch (UnsupportedEncodingException ex)
		{
		}
		finally
		{
			if (writer != null) writer.close();
		}
		return this;
	}
}
