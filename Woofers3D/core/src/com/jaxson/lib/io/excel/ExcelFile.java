package com.jaxson.lib.io.excel;

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

public class ExcelFile implements File<ExcelFile, MyWorkbook, MyWorkbook>
{
	private static final String EXTENSION_NOT_FOUND = "The file is not an Excel file.";
	public static final ExcelFile NOTHING = new ExcelFile(DefaultFile.NOTHING);

	private File file;

	public ExcelFile(File file)
	{
		this.file = file;
	}

	public ExcelFile(String path)
	{
		this(new DefaultFile(path));
	}

	@Override
	public ExcelFile append(String contents)
	{
		return new ExcelFile(getFile().append(contents));
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
	public ExcelFile copy(ExcelFile file)
	{
		return new ExcelFile(getFile().copy(file));
	}

	@Override
	public ExcelFile createDirectory()
	{
		return new ExcelFile(getFile().createDirectory());
	}

	@Override
	public ExcelFile createFile()
	{
		return new ExcelFile(getFile().createFile());
	}

	@Override
	public ExcelFile delete()
	{
		return new ExcelFile(getFile().delete());
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
	public ExcelFile getChild(String child)
	{
		return new ExcelFile(getFile().getChild(child));
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
	public ExcelFile getParent()
	{
		return new ExcelFile(getFile().getParent());
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
	public ExcelFile move(ExcelFile file)
	{
		return new ExcelFile(getFile().move(file));
	}

	@Override
	public byte[] readBytes()
	{
		return getFile().readBytes();
	}

	@Override
	public MyWorkbook readObject()
	{
		return new MyWorkbook(loadWordbook());
	}

	@Override
	public String readString()
	{
		return getFile().readString();
	}

	@Override
	public ExcelFile rename(String path)
	{
		return new ExcelFile(getFile().rename(path));
	}

	@Override
	public ExcelFile setExtension(FileType extension)
	{
		return new ExcelFile(getFile().setExtension(extension));
	}

	@Override
	public ExcelFile setExtension(String extension)
	{
		return new ExcelFile(file.setExtension(extension));
	}

	@Override
	public ExcelFile setPath(String path)
	{
		return new ExcelFile(path);
	}

	@Override
	public ExcelFile write()
	{
		return write(new MyWorkbook());
	}

	@Override
	public ExcelFile write(byte[] contents)
	{
		return new ExcelFile(getFile().write(contents));
	}

	@Override
	public ExcelFile write(MyWorkbook workbook)
	{
		FileOutputStream stream = null;
		try
		{
			stream = getFileOutputStream();
			workbook.write(stream);
		}
		catch (Exception ex)
		{
			return ExcelFile.NOTHING;
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

	@Override
	public ExcelFile write(String contents)
	{
		return new ExcelFile(getFile().write(contents));
	}

	private File getFile()
	{
		return file;
	}

	private Workbook loadWordbook()
	{
		FileType type = getExtensionType();
		if (type.equals(FileType.XLS)) return readXlsxWorkbook();
		if (type.equals(FileType.XLSX)) return readXlsxWorkbook();
		throw new IllegalArgumentException(EXTENSION_NOT_FOUND);
	}

	private HSSFWorkbook readXlsWorkbook()
	{
		HSSFWorkbook workbook = null;
		FileInputStream stream = null;
		try
		{
			stream = getFileInputStream();
			workbook = new HSSFWorkbook(stream);
		}
		catch (Exception ex)
		{
			return new HSSFWorkbook();
		}
		return workbook;
	}

	private XSSFWorkbook readXlsxWorkbook()
	{
		XSSFWorkbook workbook = null;
		FileInputStream stream = null;
		try
		{
			stream = getFileInputStream();
			workbook = new XSSFWorkbook(stream);
		}
		catch (Exception ex)
		{
			return new XSSFWorkbook();
		}
		return workbook;
	}
}
