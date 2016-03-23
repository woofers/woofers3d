package com.jaxson.lib.io.excel;

import com.jaxson.lib.io.File;
import com.jaxson.lib.io.FileType;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
import java.io.FileNotFoundException;

public class ExcelFile extends File
{
	private static final String EXTENSION_NOT_FOUND = "The file is not an Excel file.";

	public ExcelFile(String path)
	{
		super(path);
	}

	private Workbook loadWordbook()
	{
		FileType type = getExtensionType();
		if (type == FileType.XLS) return readXlsxWorkbook();
		if (type == FileType.XLSX) return readXlsxWorkbook();
		throw new IllegalArgumentException(EXTENSION_NOT_FOUND);
	}

	public MyWorkbook readWorkbook()
	{
		return new MyWorkbook(loadWordbook());
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
		catch (FileNotFoundException ex)
		{
		}
		catch (SecurityException ex)
		{
		}
		catch (IOException ex)
		{
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
		catch (FileNotFoundException ex)
		{
		}
		catch (SecurityException ex)
		{
		}
		catch (IOException ex)
		{
		}
		return workbook;
	}

	@Override
	public ExcelFile setPath(String path)
	{
		return new ExcelFile(path);
	}

	public void write(MyWorkbook workbook)
	{
		FileOutputStream stream = null;
		try
		{
			stream = getFileOutputStream();
			workbook.write(stream);
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
	}
}
