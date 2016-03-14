package com.jaxson.lib.io.excel;

import com.jaxson.lib.util.MyArrayList;
import java.util.Iterator;

public class MyColumn implements Iterable<MyCell>
{
	private MySheet sheet;
	private MyArrayList<MyCell> cells;

	protected MyColumn(MySheet sheet, MyArrayList cells)
	{
		this.sheet = sheet;
		this.cells = cells;
	}

	public MyCell getCell(int row)
	{
		return cells.get(row);
	}

	public MySheet getSheet()
	{
		return sheet;
	}

	@Override
	public Iterator<MyCell> iterator()
	{
		return cells.iterator();
	}
}
