package com.jaxson.lib.io.excel;

import com.jaxson.lib.io.DataFile;
import com.jaxson.lib.io.File;
import com.jaxson.lib.io.FileExtension;
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
import com.jaxson.lib.util.Unwrapable;

public class ExcelFile implements File<ExcelFile, MyWorkbook, MyWorkbook>, Unwrapable<MyWorkbook>
{
	public static final ExcelFile NOTHING = new ExcelFile(DataFile.NOTHING);
	private static final String EXTENSION_NOT_FOUND
			= "The file is not an Excel file.";

	private final File file;

	public ExcelFile(File file)
	{
		this.file = file;
	}

	public ExcelFile(String path)
	{
		this(new DataFile(path));
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
	public boolean equals(ExcelFile file)
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
	public ExcelFile child(String child)
	{
		return new ExcelFile(getFile().child(child));
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
	public ExcelFile parent()
	{
		return new ExcelFile(getFile().parent());
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

	public MyWorkbook unwrap()
	{
		return readObject();
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
	public ExcelFile setExtension(FileExtension extension)
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
			stream = fileOutputStream();
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
		FileExtension type = fileExtension();
		if (type.equals(FileExtension.XLS)) return readXlsxWorkbook();
		if (type.equals(FileExtension.XLSX)) return readXlsxWorkbook();
		throw new IllegalArgumentException(EXTENSION_NOT_FOUND);
	}

	private HSSFWorkbook readXlsWorkbook()
	{
		HSSFWorkbook workbook = null;
		FileInputStream stream = null;
		try
		{
			stream = fileInputStream();
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
			stream = fileInputStream();
			workbook = new XSSFWorkbook(stream);
		}
		catch (Exception ex)
		{
			return new XSSFWorkbook();
		}
		return workbook;
	}
}
