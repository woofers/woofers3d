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
import com.jaxson.lib.util.Unwrapable;
import com.jaxson.lib.util.Printer;

/**
 * A File that handles writing and reading.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class DataFile implements File<DataFile, String, String>
{
	public static final DataFile NOTHING = new EmptyFile();

	private static final String PATH_EMPTY = "Path can not be empty";

	private final String path;

	/**
	 * Constructs a {@link DataFile} from a {@link String}.
	 * @param path The path
	 */
	public DataFile(String path)
	{
		this.path = validatePath(path);
	}

	@Override
	public DataFile append(String contents)
	{
		return write(readString() + contents);
	}

	@Override
	public boolean canRead()
	{
		return javaFile().canRead();
	}

	@Override
	public boolean canWrite()
	{
		return javaFile().canWrite();
	}

	@Override
	public DataFile copy(DataFile file)
	{
		if (equals(file)) return this;
		return file.write(readBytes());
	}

	@Override
	public DataFile createDirectory()
	{
		if (exists())
		{
			if (isDirectory()) return this;
			delete();
		}
		try
		{
			javaFile().mkdirs();
		}
		catch (Exception ex)
		{
			return DataFile.NOTHING;
		}
		return this;
	}

	@Override
	public DataFile createFile()
	{
		if (exists())
		{
			if (isFile()) return this;
			delete();
		}
		return write("");
	}

	@Override
	public DataFile delete()
	{
		if (!exists()) return DataFile.NOTHING;
		try
		{
			javaFile().delete();
		}
		catch (Exception ex)
		{
			return this;
		}
		return DataFile.NOTHING;
	}

	@Override
	public boolean equals(DataFile file)
	{
		return path().equals(file.path());
	}

	@Override
	public boolean equals(Object file)
	{
		if (file instanceof DataFile) return equals((DataFile) file);
		return false;
	}

	/**
	 * Gets whether the {@link DataFile} exists.
	 * @return {@link boolean} - Whether the {@link DataFile} exists
	 */
	@Override
	public boolean exists()
	{
		return javaFile().exists();
	}

	/**
	 * Return a {@link BufferedReader} from the {@link DataFile}
	 * @return {@link BufferedReader} - The buffered reader
	 * @throws FileNotFoundException If the file is not found
	 */
	@Override
	public BufferedReader bufferedReader()
			throws FileNotFoundException
	{
		FileReader fileReader = null;
		try
		{
			fileReader = fileReader();
		}
		catch (FileNotFoundException ex)
		{
			throw ex;
		}
		return new BufferedReader(fileReader);
	}

	@Override
	public DataFile child(String child)
	{
		if (!exists() || isFile()) return DataFile.NOTHING;
		if (child.charAt(0) != FOWARD_SLASH.charAt(0))
			child = FOWARD_SLASH + child;
		DataFile file = new DataFile(path() + child);
		if (file.exists()) return file;
		return DataFile.NOTHING;
	}

	/**
	 * Gets the file extension of the {@link DataFile}. Returns an empty
	 * string if
	 * the {@link DataFile} has no extension.
	 * @return {@link String} - The file extension
	 */
	@Override
	public String extension()
	{
		String name = name();
		if (name.lastIndexOf(".") == -1) return NO_EXTENSION;
		String[] fileNames = name.split(Pattern.quote("."));
		if (fileNames.length == 1) return fileNames[0];
		return fileNames[1];
	}

	@Override
	public FileExtension fileExtension()
	{
		return new FileExtension(extension());
	}

	@Override
	public FileInputStream fileInputStream()
			throws FileNotFoundException
	{
		return new FileInputStream(javaFile());
	}

	@Override
	public FileOutputStream fileOutputStream()
			throws FileNotFoundException,
				   SecurityException
	{
		return new FileOutputStream(javaFile());
	}

	@Override
	public FileReader fileReader() throws FileNotFoundException
	{
		return new FileReader(javaFile());
	}

	/**
	 * Gets the {@link java.io.File} of the {@link DataFile}.
	 * @return {@link java.io.File} - The file
	 */
	@Override
	public java.io.File javaFile()
	{
		return new java.io.File(path());
	}

	/**
	 * Gets the file name of the {@link DataFile}.
	 * @return {@link String} - The file name
	 */
	@Override
	public String name()
	{
		int index = path().lastIndexOf(FOWARD_SLASH);
		return path().substring(index + 1);
	}

	@Override
	public String nameWithoutExtension()
	{
		String name = name();
		int index = name.lastIndexOf(".");
		if (index == -1) return name;
		return name.substring(0, index);
	}

	@Override
	public DataFile parent()
	{
		return new DataFile(parentPath());
	}

	@Override
	public String parentPath()
	{
		int index = path().lastIndexOf(FOWARD_SLASH);
		if (index == -1) return "";
		return path().substring(0, index + 1);
	}

	/**
	 * Gets the file path of the {@link DataFile}.
	 * @return {@link String} - The file path
	 */
	@Override
	public String path()
	{
		return path;
	}

	@Override
	public PrintWriter printWriter()
			throws FileNotFoundException,
				   UnsupportedEncodingException
	{
		return new PrintWriter(javaFile());
	}

	@Override
	public Date lastModified()
	{
		Calendar calendar = Calendar.getInstance();
		int utcOffset = calendar.get(Calendar.ZONE_OFFSET)
					  + calendar.get(Calendar.DST_OFFSET);
		return new Date(javaFile().lastModified() - utcOffset);
	}

	/**
	 * Gets whether the {@link DataFile} is a directory.
	 * @return {@link boolean} - Whether the {@link DataFile} is a directory
	 */
	@Override
	public boolean isDirectory()
	{
		return javaFile().isDirectory();
	}

	/**
	 * Gets whether the {@link DataFile} is a file.
	 * @return {@link boolean} - Whether the {@link DataFile} is a file
	 */
	@Override
	public boolean isFile()
	{
		return !isDirectory();
	}

	/**
	 * Gets the size of the {@link DataFile} in {@link byte}s.
	 * @return {@link long} - The size in {@link byte}s
	 */
	@Override
	public long size()
	{
		return javaFile().length();
	}

	@Override
	public DataFile move(DataFile file)
	{
		DataFile copy = copy(file);
		if (copy.equals(DataFile.NOTHING)) return this;
		delete();
		return copy;
	}

	/**
	 * Parses a the {@link DataFile} as a {@link byte} array.
	 * @return {@link byte[]} - The contents of the file as a {@link byte} array
	 */
	@Override
	public byte[] readBytes()
	{
		FileInputStream stream = null;
		byte bytes[] = new byte[(int) size()];
		try
		{
			stream = fileInputStream();
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

	public String unwrap()
	{
		return readString();
	}

	/**
	 * Parses a the {@link DataFile} as a {@link String}.
	 * @return {@link String} - The contents of the file
	 */
	@Override
	public String readString()
	{
		BufferedReader reader = null;
		String output = "";
		try
		{
			reader = bufferedReader();
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
	public DataFile rename(String name)
	{
		return rename(new DataFile(name));
	}

	@Override
	public DataFile setExtension(FileExtension extension)
	{
		if (extension.equals(fileExtension())) return this;
		return new DataFile(parentPath()
							   + nameWithoutExtension()
							   + "." + extension.extension());
	}

	@Override
	public DataFile setExtension(String extension)
	{
		return setExtension(new FileExtension(extension));
	}

	@Override
	public DataFile setPath(String path)
	{
		return new DataFile(path);
	}

	@Override
	public String toString()
	{
		return new Printer(getClass(),
				new Printer.Label("Path", path())).toString();
	}

	@Override
	public DataFile write()
	{
		return write("");
	}

	/**
	 * Writes to the {@link DataFile}.
	 * @param contents The contents to write as {@link byte}s
	 */
	@Override
	public DataFile write(byte[] contents)
	{
		FileOutputStream stream = null;
		try
		{
			stream = fileOutputStream();
			stream.write(contents);
		}
		catch (Exception ex)
		{
			return DataFile.NOTHING;
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
	 * Writes to the {@link DataFile}.
	 * @param contents The contents to write as a {@link String}
	 */
	@Override
	public DataFile write(String contents)
	{
		PrintWriter writer = null;
		try
		{
			writer = printWriter();
			writer.print(contents);
		}
		catch (Exception ex)
		{
			return DataFile.NOTHING;
		}
		finally
		{
			if (writer != null) writer.close();
		}
		return this;
	}

	private DataFile rename(DataFile file)
	{
		if (equals(file)) return this;
		if (!exists() || javaFile().renameTo(file.javaFile())) return file;
		return this;
	}

	private static String validatePath(String path)
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
			path = newDir.substring(0, newDir.length() - 1).toLowerCase();
		return path;
	}
}
