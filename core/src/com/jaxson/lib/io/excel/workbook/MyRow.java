package com.jaxson.lib.io.excel.workbook;

import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import com.jaxson.lib.util.MyArrayList;

public class MyRow implements Iterable<MyCell>
{
    private static final MissingCellPolicy POLICY = MyWorkbook.POLICY;

    private Row row;

    protected MyRow(Row row)
    {
        this.row = row;
    }

    public MyCell cell(CellLocation location) throws CellOutOfBoundsException
    {
        int x = location.x();
        return cell(x);
    }

    public MyCell cell(int row) throws CellOutOfBoundsException
    {
        return cell(row, POLICY);
    }

    public MyCell cell(int row,
            MissingCellPolicy policy) throws CellOutOfBoundsException
    {
        Cell cell = row().getCell(row, policy);
        if (cell == null)
            throw new CellOutOfBoundsException(location(row));
        return new MyCell(cell);
    }

    public MyCell createCell()
    {
        return createCell(lastCellIndex() + 1);
    }

    public MyCell createCell(int column)
    {
        return new MyCell(row().createCell(column));
    }

    public MyCell createCell(int column, int type)
    {
        return new MyCell(row().createCell(column, type));
    }

    public short firstCellIndex()
    {
        return row().getFirstCellNum();
    }

    public boolean hasZeroHeight()
    {
        return row().getZeroHeight();
    }

    public short height()
    {
        return row().getHeight();
    }

    public float heightInPoints()
    {
        return row().getHeightInPoints();
    }

    public int index()
    {
        return row().getRowNum();
    }

    public boolean isFormatted()
    {
        return row().isFormatted();
    }

    @Override
    public Iterator<MyCell> iterator()
    {
        MyArrayList<MyCell> cells = new MyArrayList<>();
        for (int x = 0; x < lastCellIndex(); x ++)
        {
            cells.add(cell(x));
        }
        return cells.iterator();
    }

    public int lastCellIndex()
    {
        int value = row().getLastCellNum();
        if (value == -1) return value;
        return value - 1;
    }

    public CellLocation location(int column)
    {
        return new CellLocation(column, index());
    }

    public int outlineLevel()
    {
        return row().getOutlineLevel();
    }

    public int physicalNumberOfCells()
    {
        return row().getPhysicalNumberOfCells();
    }

    public void removeCell(int cell)
    {
        removeCell(cell(cell));
    }

    public void removeCell(MyCell cell)
    {
        row().removeCell(cell.cell());
    }

    protected Row row()
    {
        return row;
    }

    public void setHeight(short height)
    {
        row().setHeight(height);
    }

    public void setHeightInPoints(float height)
    {
        row().setHeightInPoints(height);
    }

    public void setIndex(int index)
    {
        row().setRowNum(index);
    }

    public void setStyle(CellStyle style)
    {
        row().setRowStyle(style);
    }

    public void setZeroHeight(boolean height)
    {
        row().setZeroHeight(height);
    }

    public MySheet sheet()
    {
        return new MySheet(row().getSheet());
    }

    public MyCellStyle style()
    {
        return new MyCellStyle(row().getRowStyle());
    }
}
