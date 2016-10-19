package com.jaxson.lib.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import com.jaxson.lib.util.Printer;

/**
 * A EmptyFile that is returned inplace of {@code null}.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class EmptyFile extends DataFile
{
	EmptyFile()
	{
		super("Empty File");
	}

	@Override
	public DataFile append(String contents)
	{
		return this;
	}

	@Override
	public BufferedReader bufferedReader() throws FileNotFoundException
	{
		return null;
	}

	@Override
	public boolean canRead()
	{
		return false;
	}

	@Override
	public boolean canWrite()
	{
		return false;
	}

	@Override
	public DataFile child(String child)
	{
		return this;
	}

	@Override
	public DataFile copy(DataFile file)
	{
		return this;
	}

	@Override
	public DataFile createDirectory()
	{
		return this;
	}

	@Override
	public DataFile createFile()
	{
		return this;
	}

	@Override
	public DataFile delete()
	{
		return this;
	}

	@Override
	public boolean exists()
	{
		return false;
	}

	@Override
	public FileInputStream fileInputStream() throws FileNotFoundException
	{
		return null;
	}

	@Override
	public FileOutputStream fileOutputStream()
			throws FileNotFoundException, SecurityException
	{
		return null;
	}

	@Override
	public FileReader fileReader() throws FileNotFoundException
	{
		return null;
	}

	@Override
	public boolean isDirectory()
	{
		return false;
	}

	@Override
	public boolean isFile()
	{
		return false;
	}

	@Override
	public java.io.File javaFile()
	{
		return null;
	}

	@Override
	public Date lastModified()
	{
		return new Date(0);
	}

	@Override
	public DataFile move(DataFile file)
	{
		return this;
	}

	@Override
	public DataFile parent()
	{
		return this;
	}

	@Override
	public String parentPath()
	{
		return path();
	}

	@Override
	public PrintWriter printWriter()
			throws FileNotFoundException, UnsupportedEncodingException
	{
		return null;
	}

	@Override
	public byte[] readBytes()
	{
		return new byte[(int) size()];
	}

	@Override
	public String readString()
	{
		return "";
	}

	@Override
	public DataFile rename(String name)
	{
		return this;
	}

	@Override
	public DataFile setExtension(FileExtension extension)
	{
		return this;
	}

	@Override
	public DataFile setPath(String path)
	{
		return this;
	}

	@Override
	public long size()
	{
		return 0;
	}

	@Override
	public String toString()
	{
		return new Printer(getClass(),
				new Printer.Label()).toString();
	}

	@Override
	public DataFile write(byte[] contents)
	{
		return this;
	}

	@Override
	public DataFile write(String contents)
	{
		return this;
	}
}
