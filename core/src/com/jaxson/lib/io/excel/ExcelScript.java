package com.jaxson.lib.io.excel;

import com.jaxson.lib.io.excel.workbook.MyWorkbook;

public abstract class ExcelScript
{
    protected static final String SAVE_PREFIX = "Output ";

    private ExcelFile file;
    private MyWorkbook workbook;

    public ExcelScript(ExcelFile file)
    {
        this.file = file;
        this.workbook = file.readObject();
    }

    public ExcelScript(String path)
    {
        this(new ExcelFile(path));
    }

    protected MyWorkbook getWorkbook()
    {
        return workbook;
    }

    public abstract void run();

    public void save()
    {
        file.rename(SAVE_PREFIX + file.name()).write(workbook);
    }
}
