package com.jaxson.lib.io;

import com.jaxson.lib.io.DefaultFile;
import com.jaxson.lib.io.File;
import com.jaxson.lib.io.FileType;
import com.jaxson.lib.io.excel.workbook.MyWorkbook;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.google.gson.Gson;

public class JsonFile<T extends Object> implements File<JsonFile<T>, T, T>
{
	public static final JsonFile NOTHING = new JsonFile(DefaultFile.NOTHING, Object.class);
	private static final String EMPTY = "{" + NEXT_LINE + "}";

	private File file;
	private Class<T> type;

	public JsonFile(File file, Class<T> type)
	{
		this.file = file;
		this.type = type;
	}

	public JsonFile(String path, Class<T> type)
	{
		this(new DefaultFile(path), type);
	}

	@Override
	public JsonFile<T> append(String contents)
	{
		return new JsonFile<T>(getFile().append(contents), type);
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
		return new JsonFile<T>(getFile().copy(file), type);
	}

	@Override
	public JsonFile<T> createDirectory()
	{
		return new JsonFile<T>(getFile().createDirectory(), type);
	}

	@Override
	public JsonFile<T> createFile()
	{
		return new JsonFile<T>(getFile().createFile(), type);
	}

	@Override
	public JsonFile<T> delete()
	{
		return new JsonFile<T>(getFile().delete(), type);
	}

	@Override
	public boolean equals(File file)
	{
		return getFile().equals(file);
	}

	@Override
	public boolean exists()
	{
		return getFile().exists();
	}

	@Override
	public BufferedReader getBufferedReader() throws FileNotFoundException
	{
		return getFile().getBufferedReader();
	}

	@Override
	public JsonFile<T> getChild(String child)
	{
		return new JsonFile<T>(getFile().getChild(child), type);
	}

	@Override
	public String getExtension()
	{
		return getFile().getExtension();
	}

	@Override
	public FileType getExtensionType()
	{
		return getFile().getExtensionType();
	}

	private File getFile()
	{
		return file;
	}

	@Override
	public FileInputStream getFileInputStream() throws FileNotFoundException
	{
		return getFile().getFileInputStream();
	}

	@Override
	public FileOutputStream getFileOutputStream() throws FileNotFoundException, SecurityException
	{
		return getFile().getFileOutputStream();
	}

	@Override
	public FileReader getFileReader() throws FileNotFoundException
	{
		return getFile().getFileReader();
	}

	@Override
	public java.io.File getJavaFile()
	{
		return getFile().getJavaFile();
	}

	@Override
	public String getName()
	{
		return getFile().getName();
	}

	@Override
	public JsonFile<T> getParent()
	{
		return new JsonFile<T>(getFile().getParent(), type);
	}

	@Override
	public String getParentPath()
	{
		return getFile().getParentPath();
	}

	@Override
	public String getPath()
	{
		return getFile().getPath();
	}

	@Override
	public PrintWriter getPrintWriter() throws FileNotFoundException, UnsupportedEncodingException
	{
		return getFile().getPrintWriter();
	}

	@Override
	public Date getWhenLastModified()
	{
		return getFile().getWhenLastModified();
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
	public long length()
	{
		return getFile().length();
	}

	@Override
	public JsonFile<T> move(JsonFile<T> file)
	{
		return new JsonFile<T>(getFile().move(file), type);
	}

	@Override
	public byte[] readBytes()
	{
		return getFile().readBytes();
	}

	@Override
	public String readString()
	{
		return getFile().readString();
	}

	@Override
	public T readObject()
	{
		return new Gson().fromJson(readString(), type);
	}

	@Override
	public JsonFile<T> rename(String path)
	{
		return new JsonFile<T>(getFile().rename(path), type);
	}

	@Override
	public JsonFile<T> setExtension(FileType extension)
	{
		return new JsonFile<T>(getFile().setExtension(extension), type);
	}

	@Override
	public JsonFile<T> setExtension(String extension)
	{
		return new JsonFile<T>(file.setExtension(extension), type);
	}

	@Override
	public JsonFile<T> setPath(String path)
	{
		return new JsonFile<T>(path, type);
	}

	@Override
	public JsonFile<T> write()
	{
		return write(EMPTY);
	}

	@Override
	public JsonFile<T> write(byte[] contents)
	{
		return new JsonFile<T>(getFile().write(contents), type);
	}

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

	@Override
	public JsonFile<T> write(String contents)
	{
		return new JsonFile<T>(getFile().write(contents), type);
	}
}
