package com.jaxson.lib.io.excel.workbook;

public class CellOutOfBoundsException extends IndexOutOfBoundsException
{
	private static final long serialVersionUID = 2679031450699578322L;
	private static final String OUT_OF_RANGE = " is out of range";

	public CellOutOfBoundsException()
	{
		super();
	}

	public CellOutOfBoundsException(CellLocation location)
	{
		super(location.toString() + OUT_OF_RANGE);
	}

	public CellOutOfBoundsException(String var)
	{
		super(var + OUT_OF_RANGE);
	}
}
