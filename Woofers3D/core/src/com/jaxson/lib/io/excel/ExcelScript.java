package com.jaxson.lib.io.excel;

public abstract class ExcelScript
{
	protected static final String SAVE_PREFIX = "output ";
	private static final boolean SAVES = true;

	private ExcelFile file;
	private MyWorkbook workbook;

	public ExcelScript(ExcelFile file)
	{
		this.file = file;
	}

	public ExcelScript(String path)
	{
		this(new ExcelFile(path));
	}

	protected MyWorkbook getWorkbook()
	{
		return workbook;
	}

	public void run()
	{
		this.workbook = file.readWorkbook();
	}

	public void save()
	{
		System.out.println(file.getPathWithoutName() + SAVE_PREFIX + file.getName());
		file.setPath(file.getPathWithoutName() + SAVE_PREFIX + file.getName()).write(workbook);
	}
}
