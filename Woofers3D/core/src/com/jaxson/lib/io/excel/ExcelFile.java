package com.jaxson.lib.io.excel;

import com.jaxson.lib.io.File;
import com.jaxson.lib.io.FileType;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
		if (type.equals(FileType.XLS)) return readXlsxWorkbook();
		if (type.equals(FileType.XLSX)) return readXlsxWorkbook();
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

	@Override
	public ExcelFile setPath(String path)
	{
		return new ExcelFile(path);
	}

	public ExcelFile write()
	{
		return write(new MyWorkbook());
	}

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
}
