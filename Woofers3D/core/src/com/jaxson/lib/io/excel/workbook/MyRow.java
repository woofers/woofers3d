package com.jaxson.lib.io.excel.workbook;

import com.jaxson.lib.util.MyArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

public class MyRow implements Iterable<MyCell>
{
	private static final MissingCellPolicy POLICY = MyWorkbook.POLICY;

	private Row row;

	protected MyRow(Row row)
	{
		this.row = row;
	}

	public MyCell createCell()
	{
		return createCell(getLastCellIndex() + 1);
	}

	public MyCell createCell(int column)
	{
		return new MyCell(getRow().createCell(column));
	}

	public MyCell createCell(int column, int type)
	{
		return new MyCell(getRow().createCell(column, type));
	}

	public MyCell getCell(CellLocation location)
			throws CellOutOfBoundsException
	{
		int x = location.getX();
		return getCell(x);
	}

	public MyCell getCell(int row) throws CellOutOfBoundsException
	{
		return getCell(row, POLICY);
	}

	public MyCell getCell(int row, MissingCellPolicy policy)
			throws CellOutOfBoundsException
	{
		Cell cell = getRow().getCell(row, policy);
		if (cell == null)
			throw new CellOutOfBoundsException(getCellLocation(row));
		return new MyCell(cell);
	}

	public CellLocation getCellLocation(int column)
	{
		return new CellLocation(column, getIndex());
	}

	public short getFirstCellIndex()
	{
		return getRow().getFirstCellNum();
	}

	public short getHeight()
	{
		return getRow().getHeight();
	}

	public float getHeightInPoints()
	{
		return getRow().getHeightInPoints();
	}

	public int getIndex()
	{
		return getRow().getRowNum();
	}

	public int getLastCellIndex()
	{
		int value = getRow().getLastCellNum();
		if (value == -1) return value;
		return value - 1;
	}

	public int getOutlineLevel()
	{
		return getRow().getOutlineLevel();
	}

	public int getPhysicalNumberOfCells()
	{
		return getRow().getPhysicalNumberOfCells();
	}

	public CellStyle getRowStyle()
	{
		return getRow().getRowStyle();
	}

	public MySheet getSheet()
	{
		return new MySheet(getRow().getSheet());
	}

	public boolean getZeroHeight()
	{
		return getRow().getZeroHeight();
	}

	public boolean isFormatted()
	{
		return getRow().isFormatted();
	}

	@Override
	public Iterator<MyCell> iterator()
	{
		MyArrayList<MyCell> cells = new MyArrayList<>();
		for (int x = 0; x < getLastCellIndex(); x ++)
		{
			cells.add(getCell(x));
		}
		return cells.iterator();
	}

	public void removeCell(int cell)
	{
		removeCell(getCell(cell));
	}

	public void removeCell(MyCell cell)
	{
		getRow().removeCell(cell.getCell());
	}

	public void setHeight(short height)
	{
		getRow().setHeight(height);
	}

	public void setHeightInPoints(float height)
	{
		getRow().setHeightInPoints(height);
	}

	public void setRowIndex(int index)
	{
		getRow().setRowNum(index);
	}

	public void setRowStyle(CellStyle style)
	{
		getRow().setRowStyle(style);
	}

	public void setZeroHeight(boolean height)
	{
		getRow().setZeroHeight(height);
	}

	protected Row getRow()
	{
		return row;
	}
}
