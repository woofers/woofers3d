package com.jaxson.lib.io;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * A EmptyFile that is returned inplace of {@code null}.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class EmptyFile extends DefaultFile
{
	EmptyFile()
	{
		super("Empty File");
	}

	@Override
	public DefaultFile append(String contents)
	{
		return this;
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
	public DefaultFile copy(DefaultFile file)
	{
		return this;
	}

	@Override
	public DefaultFile createDirectory()
	{
		return this;
	}

	@Override
	public DefaultFile createFile()
	{
		return this;
	}

	@Override
	public DefaultFile delete()
	{
		return this;
	}

	@Override
	public boolean exists()
	{
		return false;
	}

	@Override
	public DefaultFile getChild(String child)
	{
		return this;
	}

	@Override
	public java.io.File getJavaFile()
	{
		return null;
	}

	@Override
	public DefaultFile getParent()
	{
		return new DefaultFile(getParentPath());
	}

	@Override
	public String getParentPath()
	{
		return getPath();
	}

	@Override
	public PrintWriter getPrintWriter() throws FileNotFoundException, UnsupportedEncodingException
	{
		return new PrintWriter(getJavaFile());
	}

	@Override
	public Date getWhenLastModified()
	{
		return new Date(0);
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
	public long length()
	{
		return 0;
	}

	@Override
	public DefaultFile move(DefaultFile file)
	{
		return this;
	}

	@Override
	public byte[] readBytes()
	{
		return new byte[(int) length()];
	}

	@Override
	public String readString()
	{
		return "";
	}

	@Override
	public DefaultFile rename(String name)
	{
		return this;
	}

	@Override
	public DefaultFile setExtension(FileType extension)
	{
		return this;
	}

	@Override
	public DefaultFile setPath(String path)
	{
		return this;
	}

	@Override
	public DefaultFile write(byte[] contents)
	{
		return this;
	}

	@Override
	public DefaultFile write(String contents)
	{
		return this;
	}
}
