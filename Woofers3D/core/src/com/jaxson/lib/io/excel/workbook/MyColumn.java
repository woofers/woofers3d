package com.jaxson.lib.io.excel.workbook;

import java.util.Iterator;
import com.jaxson.lib.util.MyArrayList;

public class MyColumn implements Iterable<MyCell>
{
	private MySheet sheet;
	private MyArrayList<MyCell> cells;

	protected MyColumn(MySheet sheet, MyArrayList<MyCell> cells)
	{
		this.sheet = sheet;
		this.cells = cells;
	}

	public MyCell cell(CellLocation cell)
	{
		return cell(cell.y());
	}

	public MyCell cell(int row)
	{
		return cells.get(row);
	}

	public int index()
	{
		return cells.get(0).x();
	}

	@Override
	public Iterator<MyCell> iterator()
	{
		return cells.iterator();
	}

	public MySheet sheet()
	{
		return sheet;
	}

	public MyCellStyle style()
	{
		return sheet().columnStyle(index());
	}

	public int width()
	{
		return sheet().columnWidth(index());
	}
}
