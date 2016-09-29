package com.jaxson.lib.io;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import com.jaxson.lib.util.Unwrapable;

public class JsonFile<T> implements File<JsonFile<T>, T, T>, Unwrapable<T>
{
	private static final String EMPTY = "{" + NEXT_LINE + NEXT_LINE + "}";
	public static final JsonFile NOTHING
			= new JsonFile(DataFile.NOTHING, Object.class);

	private final File file;
	private final Class<T> type;

	public JsonFile(File file, Class<T> type)
	{
		this.file = file;
		this.type = type;
	}

	public JsonFile(String path, Class<T> type)
	{
		this(new DataFile(path), type);
	}

	@Override
	public JsonFile<T> append(String contents)
	{
		return new JsonFile<>(getFile().append(contents), type);
	}

	@Override
	public boolean canRead()
	{
		return getFile().canRead();
	}

	@Override
	public boolean canWrite()
	{
		return getFile().canWrite();
	}

	@Override
	public JsonFile<T> copy(JsonFile<T> file)
	{
		return new JsonFile<>(getFile().copy(file), type);
	}

	@Override
	public JsonFile<T> createDirectory()
	{
		return new JsonFile<>(getFile().createDirectory(), type);
	}

	@Override
	public JsonFile<T> createFile()
	{
		return new JsonFile<>(getFile().createFile(), type);
	}

	@Override
	public JsonFile<T> delete()
	{
		return new JsonFile<>(getFile().delete(), type);
	}

	@Override
	public boolean equals(JsonFile file)
	{
		return getFile().equals(file);
	}

	@Override
	public boolean exists()
	{
		return getFile().exists();
	}

	@Override
	public BufferedReader bufferedReader()
			throws FileNotFoundException
	{
		return getFile().bufferedReader();
	}

	@Override
	public JsonFile<T> child(String child)
	{
		return new JsonFile<>(getFile().child(child), type);
	}

	@Override
	public String extension()
	{
		return getFile().extension();
	}

	@Override
	public FileExtension fileExtension()
	{
		return getFile().fileExtension();
	}

	@Override
	public FileInputStream fileInputStream()
			throws FileNotFoundException
	{
		return getFile().fileInputStream();
	}

	@Override
	public FileOutputStream fileOutputStream()
			throws FileNotFoundException,
				   SecurityException
	{
		return getFile().fileOutputStream();
	}

	@Override
	public FileReader fileReader()
			throws FileNotFoundException
	{
		return getFile().fileReader();
	}

	@Override
	public java.io.File javaFile()
	{
		return getFile().javaFile();
	}

	@Override
	public String name()
	{
		return getFile().name();
	}

	@Override
	public String nameWithoutExtension()
	{
		return getFile().nameWithoutExtension();
	}

	@Override
	public JsonFile<T> parent()
	{
		return new JsonFile<>(getFile().parent(), type);
	}

	@Override
	public String parentPath()
	{
		return getFile().parentPath();
	}

	@Override
	public String path()
	{
		return getFile().path();
	}

	@Override
	public PrintWriter printWriter()
			throws FileNotFoundException,
				   UnsupportedEncodingException
	{
		return getFile().printWriter();
	}

	@Override
	public Date lastModified()
	{
		return getFile().lastModified();
	}

	@Override
	public boolean isDirectory()
	{
		return getFile().isDirectory();
	}

	@Override
	public boolean isFile()
	{
		return getFile().isFile();
	}

	@Override
	public long size()
	{
		return getFile().size();
	}

	@Override
	public JsonFile<T> move(JsonFile<T> file)
	{
		return new JsonFile<>(getFile().move(file), type);
	}

	@Override
	public byte[] readBytes()
	{
		return getFile().readBytes();
	}

	public T unwrap()
	{
		return readObject();
	}

	@Override
	public T readObject()
	{
		T object = null;
		try
		{
			object = new Gson().fromJson(readString(), type);
		}
		catch (Exception ex)
		{
			return null;
		}
		return object;
	}

	@Override
	public String readString()
	{
		return getFile().readString();
	}

	@Override
	public JsonFile<T> rename(String path)
	{
		return new JsonFile<>(getFile().rename(path), type);
	}

	@Override
	public JsonFile<T> setExtension(FileExtension extension)
	{
		return new JsonFile<>(getFile().setExtension(extension), type);
	}

	@Override
	public JsonFile<T> setExtension(String extension)
	{
		return new JsonFile<>(file.setExtension(extension), type);
	}

	@Override
	public JsonFile<T> setPath(String path)
	{
		return new JsonFile<>(path, type);
	}

	@Override
	public JsonFile<T> write()
	{
		return write(EMPTY);
	}

	@Override
	public JsonFile<T> write(byte[] contents)
	{
		return new JsonFile<>(getFile().write(contents), type);
	}

	@Override
	public JsonFile<T> write(String contents)
	{
		return new JsonFile<>(getFile().write(contents), type);
	}

	@Override
	public JsonFile<T> write(T object)
	{
		try
		{
			write(new Gson().toJson(object));
		}
		catch (Exception ex)
		{
			return JsonFile.NOTHING;
		}
		return this;
	}

	private File getFile()
	{
		return file;
	}
}
