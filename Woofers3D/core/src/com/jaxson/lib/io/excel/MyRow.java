package com.jaxson.lib.io.excel;

import com.jaxson.lib.io.excel.MyCell.CellLocation;
import com.jaxson.lib.io.excel.MyCell.CellOutOfBoundsException;
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

	public MyCell createCell(int column)
	{
		return new MyCell(getRow().createCell(column));
	}

	public MyCell createCell(int column, int type)
	{
		return new MyCell(getRow().createCell(column, type));
	}

	public MyCell getCell(CellLocation location) throws CellOutOfBoundsException
	{
		int x = location.getX();
		MyCell cell = getCell(x);
		return cell;
	}

	public MyCell getCell(int row) throws CellOutOfBoundsException
	{
		return getCell(row, POLICY);
	}

	public MyCell getCell(int row, MissingCellPolicy policy) throws CellOutOfBoundsException
	{
		Cell cell = getRow().getCell(row, policy);
		if (cell == null) throw new CellOutOfBoundsException(getCellLocation(row));
		return new MyCell(cell);
	}

	public CellLocation getCellLocation(int column)
	{
		return new CellLocation(column, getRowIndex());
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

	public int getLastCellIndex()
	{
		return getRow().getLastCellNum() - 1;
	}

	public int getOutlineLevel()
	{
		return getRow().getOutlineLevel();
	}

	public int getPhysicalNumberOfCells()
	{
		return getRow().getPhysicalNumberOfCells();
	}

	protected Row getRow()
	{
		return row;
	}

	public int getRowIndex()
	{
		return getRow().getRowNum();
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
		MyArrayList<MyCell> cells = new MyArrayList<MyCell>();
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
}
