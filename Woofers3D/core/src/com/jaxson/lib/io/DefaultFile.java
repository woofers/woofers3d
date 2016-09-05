package com.jaxson.lib.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * A File that handles writing and reading.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class DefaultFile implements File<DefaultFile, String, String>
{
	public static final DefaultFile NOTHING = new EmptyFile();
	private static final String PATH_EMPTY = "Path can not be empty";

	private String path;

	/**
	 * Constructs a {@link DefaultFile} from a {@link String}.
	 * @param path The path
	 */
	public DefaultFile(String path)
	{
		this.path = path;
		validatePath();
	}

	@Override
	public DefaultFile append(String contents)
	{
		return write(readString() + contents);
	}

	@Override
	public boolean canRead()
	{
		return getJavaFile().canRead();
	}

	@Override
	public boolean canWrite()
	{
		return getJavaFile().canWrite();
	}

	@Override
	public DefaultFile copy(DefaultFile file)
	{
		if (equals(file)) return this;
		return file.write(readBytes());
	}

	@Override
	public DefaultFile createDirectory()
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
		catch (Exception ex)
		{
			return DefaultFile.NOTHING;
		}
		return this;
	}

	@Override
	public DefaultFile createFile()
	{
		if (exists())
		{
			if (isFile()) return this;
			delete();
		}
		return write("");
	}

	@Override
	public DefaultFile delete()
	{
		if (!exists()) return DefaultFile.NOTHING;
		try
		{
			getJavaFile().delete();
		}
		catch (Exception ex)
		{
			return this;
		}
		return DefaultFile.NOTHING;
	}

	@Override
	public boolean equals(File file)
	{
		return equals(file.getPath());
	}

	@Override
	public boolean equals(Object file)
	{
		if (file instanceof DefaultFile) return equals((DefaultFile) file);
		return false;
	}

	/**
	 * Gets whether the {@link DefaultFile} exists.
	 * @return {@link boolean} - Whether the {@link DefaultFile} exists
	 */
	@Override
	public boolean exists()
	{
		return getJavaFile().exists();
	}

	/**
	 * Return a {@link BufferedReader} from the {@link DefaultFile}
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

	@Override
	public DefaultFile getChild(String child)
	{
		if (!exists() || isFile()) return DefaultFile.NOTHING;
		if (child.charAt(0) != FOWARD_SLASH.charAt(0)) child = FOWARD_SLASH + child;
		DefaultFile file = new DefaultFile(getPath() + child);
		if (file.exists()) return file;
		return DefaultFile.NOTHING;
	}

	/**
	 * Gets the file extension of the {@link DefaultFile}. Returns an empty
	 * string if
	 * the {@link DefaultFile} has no extension.
	 * @return {@link String} - The file extension
	 */
	@Override
	public String getExtension()
	{
		String name = getName();
		if (name.lastIndexOf(".") == -1) return NO_EXTENSION;
		String[] fileNames = name.split(Pattern.quote("."));
		if (fileNames.length == 1) return fileNames[0];
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
	 * Gets the {@link java.io.File} of the {@link DefaultFile}.
	 * @return {@link java.io.File} - The file
	 */
	@Override
	public java.io.File getJavaFile()
	{
		return new java.io.File(getPath());
	}

	/**
	 * Gets the file name of the {@link DefaultFile}.
	 * @return {@link String} - The file name
	 */
	@Override
	public String getName()
	{
		int index = getPath().lastIndexOf(FOWARD_SLASH);
		return getPath().substring(index + 1);
	}

	@Override
	public DefaultFile getParent()
	{
		return new DefaultFile(getParentPath());
	}

	@Override
	public String getParentPath()
	{
		int index = getPath().lastIndexOf(FOWARD_SLASH);
		if (index == -1) return "";
		return getPath().substring(0, index + 1);
	}

	/**
	 * Gets the file path of the {@link DefaultFile}.
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

	@Override
	public Date getWhenLastModified()
	{
		Calendar calendar = Calendar.getInstance();
		int utcOffset = calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET);
		return new Date(getJavaFile().lastModified() - utcOffset);
	}

	/**
	 * Gets whether the {@link DefaultFile} is a directory.
	 * @return {@link boolean} - Whether the {@link DefaultFile} is a directory
	 */
	@Override
	public boolean isDirectory()
	{
		return getJavaFile().isDirectory();
	}

	/**
	 * Gets whether the {@link DefaultFile} is a file.
	 * @return {@link boolean} - Whether the {@link DefaultFile} is a file
	 */
	@Override
	public boolean isFile()
	{
		return !isDirectory();
	}

	/**
	 * Gets the size of the {@link DefaultFile} in {@link byte}s.
	 * @return {@link long} - The size in {@link byte}s
	 */
	@Override
	public long length()
	{
		return getJavaFile().length();
	}

	@Override
	public DefaultFile move(DefaultFile file)
	{
		DefaultFile copy = copy(file);
		if (copy.equals(DefaultFile.NOTHING)) return this;
		delete();
		return copy;
	}

	/**
	 * Parses a the {@link DefaultFile} as a {@link byte} array.
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
		catch (Exception ex)
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

	@Override
	public String readObject()
	{
		return readString();
	}

	/**
	 * Parses a the {@link DefaultFile} as a {@link String}.
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
		catch (Exception ex)
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
	public DefaultFile rename(String name)
	{
		return rename(new DefaultFile(name));
	}

	@Override
	public DefaultFile setExtension(FileType extension)
	{
		if (extension.equals(getExtensionType())) return this;
		int index = getPath().lastIndexOf(".");
		if (index == -1) index = getPath().length();
		return new DefaultFile(getPath().substring(0, index) + "." + extension.getExtension());
	}

	@Override
	public DefaultFile setExtension(String extension)
	{
		return setExtension(new FileType(extension));
	}

	@Override
	public DefaultFile setPath(String path)
	{
		return new DefaultFile(path);
	}

	@Override
	public DefaultFile write()
	{
		return write("");
	}

	/**
	 * Writes to the {@link DefaultFile}.
	 * @param contents The contents to write as {@link byte}s
	 */
	@Override
	public DefaultFile write(byte[] contents)
	{
		FileOutputStream stream = null;
		try
		{
			stream = getFileOutputStream();
			stream.write(contents);
		}
		catch (Exception ex)
		{
			return DefaultFile.NOTHING;
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
	 * Writes to the {@link DefaultFile}.
	 * @param contents The contents to write as a {@link String}
	 */
	@Override
	public DefaultFile write(String contents)
	{
		PrintWriter writer = null;
		try
		{
			writer = getPrintWriter();
			writer.print(contents);
		}
		catch (Exception ex)
		{
			return DefaultFile.NOTHING;
		}
		finally
		{
			if (writer != null) writer.close();
		}
		return this;
	}

	protected boolean equals(String path)
	{
		return getPath().equals(path);
	}

	private DefaultFile rename(DefaultFile file)
	{
		if (equals(file)) return this;
		if (getJavaFile().renameTo(file.getJavaFile())) return file;
		return this;
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
		if (!newDir.isEmpty()) path = newDir.substring(0, newDir.length() - 1).toLowerCase();
	}
}
