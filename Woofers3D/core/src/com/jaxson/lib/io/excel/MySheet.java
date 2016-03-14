package com.jaxson.lib.io.excel;

import com.jaxson.lib.io.excel.MyCell.CellLocation;
import com.jaxson.lib.io.excel.MyCell.CellOutOfBoundsException;
import com.jaxson.lib.util.MyArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.util.PaneInformation;
import org.apache.poi.ss.usermodel.AutoFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellRange;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.util.CellRangeAddress;

public class MySheet implements Iterable<MyRow>
{
	private Sheet sheet;

	public MySheet(Sheet sheet)
	{
		this.sheet = sheet;
	}

	public int addMergedRegion(CellRangeAddress region)
	{
		return getSheet().addMergedRegion(region);
	}

	public void addValidationData(DataValidation dataValidation)
	{
		getSheet().addValidationData(dataValidation);
	}

	public void autoSizeColumn(int column)
	{
		getSheet().autoSizeColumn(column);
	}

	public void autoSizeColumn(int column, boolean mergedCells)
	{
		getSheet().autoSizeColumn(column, mergedCells);
	}

	public Iterable<MyColumn> columns()
	{
		MyArrayList<MyColumn> columns = new MyArrayList<MyColumn>();
		MyRow topRow = iterator().next();
		CellLocation location = null;
		MyCell cellRow = null;
		for (MyCell cell: topRow)
		{
			MyArrayList<MyCell> cells = new MyArrayList<MyCell>();
			location = cell.getLocation();
			cellRow = getCell(location);
			do
			{
				cells.add(cellRow);
				location = location.nextRow(1);
				try
				{
					cellRow = getCell(location);
				}
				catch (Exception ex)
				{
					cellRow = null;
				}
			}
			while (cellRow != null);
			columns.add(new MyColumn(this, cells));
		}
		return columns;
	}

	public Drawing createDrawingPatriarch()
	{
		return getSheet().createDrawingPatriarch();
	}

	public void createFreezePane(int columnSplit, int rowSplit)
	{
		getSheet().createFreezePane(columnSplit, rowSplit);
	}

	public void createFreezePane(int columnSplit, int rowSplit, int leftmostColumn, int topRow)
	{
		getSheet().createFreezePane(columnSplit, rowSplit, leftmostColumn, topRow);
	}

	public Row createRow(int index)
	{
		return getSheet().createRow(index);
	}

	public void createSplitPane(int xSplit, int ySplit, int leftMostColumn, int topRow, int activePane)
	{
		getSheet().createSplitPane(xSplit, ySplit, leftMostColumn, topRow, activePane);
	}

	public int getAmountMergedRegions()
	{
		return getSheet().getNumMergedRegions();
	}

	public boolean getAutobreaks()
	{
		return getSheet().getAutobreaks();
	}

	public MyCell getCell(CellLocation location) throws CellOutOfBoundsException
	{
		int y = location.getY() + 1;
		Iterator<MyRow> iterator = iterator();
		MyRow row = null;
		for (int r = 0; r < y; r ++)
		{
			if (iterator.hasNext())
				row = iterator.next();
			else
				throw new CellOutOfBoundsException(location);
		}
		if (row == null) throw new CellOutOfBoundsException(location);
		return row.getCell(location);
	}

	public MyCell getCell(int x, int y)
	{
		return getCell(new CellLocation(x, y));
	}

	public MyCell getCell(String cell)
	{
		return getCell(new CellLocation(cell));
	}

	public Comment getCellComment(CellLocation location)
	{
		return getCellComment(location.getX(), location.getY());
	}

	public Comment getCellComment(int column, int row)
	{
		return getSheet().getCellComment(row, column);
	}

	public MyColumn getColumn(CellLocation location)
	{
		return getColumn(location.getX());
	}

	public MyColumn getColumn(int column)
	{
		MyArrayList<MyCell> cells = new MyArrayList<MyCell>();
		MyCell cell = getTopRow().getCell(column);
		CellLocation location = cell.getLocation();
		do
		{
			cells.add(cell);
			location = location.nextRow(1);
			try
			{
				cell = getCell(location);
			}
			catch (Exception ex)
			{
				cell = null;
			}
		}
		while (cell != null);
		return new MyColumn(this, cells);
	}

	public int[] getColumnBreaks()
	{
		return getSheet().getColumnBreaks();
	}

	public int getColumnOutlineLevel(int column)
	{
		return getSheet().getColumnOutlineLevel(column);
	}

	public CellStyle getColumnStyle(int column)
	{
		return getSheet().getColumnStyle(column);
	}

	public int getColumnWidth(int column)
	{
		return getSheet().getColumnWidth(column);
	}

	public float getColumnWidthInPixels(int column)
	{
		return getSheet().getColumnWidthInPixels(column);
	}

	public DataValidationHelper getDataValidationHelper()
	{
		return getSheet().getDataValidationHelper();
	}

	public List<? extends DataValidation> getDataValidations()
	{
		return getSheet().getDataValidations();
	}

	public int getDefaultColumnWidth()
	{
		return getSheet().getDefaultColumnWidth();
	}

	public short getDefaultRowHeight()
	{
		return getSheet().getDefaultRowHeight();
	}

	public float getDefaultRowHeightInPoints()
	{
		return getSheet().getDefaultRowHeightInPoints();
	}

	public boolean getDisplayGuts()
	{
		return getSheet().getDisplayGuts();
	}

	public MyRow getFirstRow()
	{
		return getRow(getFirstRowIndex());
	}

	public int getFirstRowIndex()
	{
		return getSheet().getFirstRowNum();
	}

	public boolean getFitToPage()
	{
		return getSheet().getFitToPage();
	}

	public Footer getFooter()
	{
		return getSheet().getFooter();
	}

	public boolean getForceFormulaRecalculation()
	{
		return getSheet().getForceFormulaRecalculation();
	}

	public Header getHeader()
	{
		return getSheet().getHeader();
	}

	public boolean getHorizontallyCenter()
	{
		return getSheet().getHorizontallyCenter();
	}

	public int getIndex()
	{
		return getWorkbook().getSheetIndex(this);
	}

	public MyRow getLastRow()
	{
		return getRow(getLastRowIndex());
	}

	public int getLastRowIndex()
	{
		return getSheet().getLastRowNum();
	}

	public short getLeftColumn()
	{
		return getSheet().getLeftCol();
	}

	public double getMargin(short margin)
	{
		return getSheet().getMargin(margin);
	}

	public CellRangeAddress getMergedRegion(int index)
	{
		return getSheet().getMergedRegion(index);
	}

	public List<CellRangeAddress> getMergedRegions()
	{
		return getSheet().getMergedRegions();
	}

	public String getName()
	{
		return getSheet().getSheetName();
	}

	public PaneInformation getPaneInformation()
	{
		return getSheet().getPaneInformation();
	}

	public int getPhysicalNumberOfRows()
	{
		return getSheet().getPhysicalNumberOfRows();
	}

	public PrintSetup getPrintSetup()
	{
		return getSheet().getPrintSetup();
	}

	public boolean getProtect()
	{
		return getSheet().getProtect();
	}

	public CellRangeAddress getRepeatingColumns()
	{
		return getSheet().getRepeatingColumns();
	}

	public CellRangeAddress getRepeatingRows()
	{
		return getSheet().getRepeatingRows();
	}

	public MyRow getRow(int index)
	{
		return new MyRow(getSheet().getRow(index));
	}

	public int[] getRowBreaks()
	{
		return getSheet().getRowBreaks();
	}

	public boolean getRowSumsBelow()
	{
		return getSheet().getRowSumsBelow();
	}

	public boolean getRowSumsRight()
	{
		return getSheet().getRowSumsRight();
	}

	public boolean getScenarioProtect()
	{
		return getSheet().getScenarioProtect();
	}

	protected Sheet getSheet()
	{
		return sheet;
	}

	public SheetConditionalFormatting getSheetConditionalFormatting()
	{
		return getSheet().getSheetConditionalFormatting();
	}

	public MyRow getTopRow()
	{
		return iterator().next();
	}

	public boolean getVerticallyCenter()
	{
		return getSheet().getVerticallyCenter();
	}

	public MyWorkbook getWorkbook()
	{
		return new MyWorkbook(getSheet().getWorkbook());
	}

	public void groupColumn(int startColumn, int endColumn)
	{
		getSheet().groupColumn(startColumn, endColumn);
	}

	public void groupRow(int startRow, int endStart)
	{
		getSheet().groupRow(startRow, endStart);
	}

	public boolean isColumnBroken(int column)
	{
		return getSheet().isColumnBroken(column);
	}

	public boolean isColumnHidden(int column)
	{
		return getSheet().isColumnHidden(column);
	}

	public boolean isDisplayFormulas()
	{
		return getSheet().isDisplayFormulas();
	}

	public boolean isDisplayGridlines()
	{
		return getSheet().isDisplayGridlines();
	}

	public boolean isDisplayRowColHeadings()
	{
		return getSheet().isDisplayRowColHeadings();
	}

	public boolean isDisplayZeros()
	{
		return getSheet().isDisplayZeros();
	}

	public boolean isPrintGridlines()
	{
		return getSheet().isPrintGridlines();
	}

	public boolean isRightToLeft()
	{
		return getSheet().isRightToLeft();
	}

	public boolean isRowBroken(int row)
	{
		return getSheet().isRowBroken(row);
	}

	public boolean isSelected()
	{
		return getSheet().isSelected();
	}

	@Override
	public Iterator<MyRow> iterator()
	{
		return rows().iterator();
	}

	public void protectSheet(String password)
	{
		getSheet().protectSheet(password);
	}

	public CellRange<? extends Cell> removeArrayFormula(Cell cell)
	{
		return getSheet().removeArrayFormula(cell);
	}

	public void removeColumnBreak(int column)
	{
		getSheet().removeColumnBreak(column);
	}

	public void removeMergedRegion(int index)
	{
		getSheet().removeMergedRegion(index);
	}

	public void removeRow(int row)
	{
		removeRow(getRow(row));
	}

	public void removeRow(MyRow row)
	{
		getSheet().removeRow(row.getRow());
	}

	public void removeRowBreak(int row)
	{
		getSheet().removeRowBreak(row);
	}

	public Iterable<MyRow> rows()
	{
		MyArrayList<MyRow> rows = new MyArrayList<MyRow>();
		for (Row row: getSheet())
		{
			rows.add(new MyRow(row));
		}
		return rows;
	}

	public CellRange<? extends Cell> setArrayFormula(String formula, CellRangeAddress range)
	{
		return getSheet().setArrayFormula(formula, range);
	}

	public void setAutobreaks(boolean autobreaks)
	{
		getSheet().setAutobreaks(autobreaks);
	}

	public AutoFilter setAutoFilter(CellRangeAddress range)
	{
		return getSheet().setAutoFilter(range);
	}

	public void setColumnBreak(int arg0)
	{
		getSheet().setColumnBreak(arg0);
	}

	public void setColumnGroupCollapsed(int arg0, boolean arg1)
	{
		getSheet().setColumnGroupCollapsed(arg0, arg1);
	}

	public void setColumnHidden(int arg0, boolean arg1)
	{
		getSheet().setColumnHidden(arg0, arg1);
	}

	public void setColumnWidth(int arg0, int arg1)
	{
		getSheet().setColumnWidth(arg0, arg1);
	}

	public void setDefaultColumnStyle(int arg0, CellStyle arg1)
	{
		getSheet().setDefaultColumnStyle(arg0, arg1);
	}

	public void setDefaultColumnWidth(int arg0)
	{
		getSheet().setDefaultColumnWidth(arg0);
	}

	public void setDefaultRowHeight(short arg0)
	{
		getSheet().setDefaultRowHeight(arg0);
	}

	public void setDefaultRowHeightInPoints(float arg0)
	{
		getSheet().setDefaultRowHeightInPoints(arg0);
	}

	public void setDisplayFormulas(boolean arg0)
	{
		getSheet().setDisplayFormulas(arg0);
	}

	public void setDisplayGridlines(boolean arg0)
	{
		getSheet().setDisplayGridlines(arg0);
	}

	public void setDisplayGuts(boolean arg0)
	{
		getSheet().setDisplayGuts(arg0);
	}

	public void setDisplayRowColHeadings(boolean arg0)
	{
		getSheet().setDisplayRowColHeadings(arg0);
	}

	public void setDisplayZeros(boolean arg0)
	{
		getSheet().setDisplayZeros(arg0);
	}

	public void setFitToPage(boolean arg0)
	{
		getSheet().setFitToPage(arg0);
	}

	public void setForceFormulaRecalculation(boolean arg0)
	{
		getSheet().setForceFormulaRecalculation(arg0);
	}

	public void setHorizontallyCenter(boolean arg0)
	{
		getSheet().setHorizontallyCenter(arg0);
	}

	public void setMargin(short arg0, double arg1)
	{
		getSheet().setMargin(arg0, arg1);
	}

	public void setPrintGridlines(boolean arg0)
	{
		getSheet().setPrintGridlines(arg0);
	}

	public void setRepeatingColumns(CellRangeAddress arg0)
	{
		getSheet().setRepeatingColumns(arg0);
	}

	public void setRepeatingRows(CellRangeAddress arg0)
	{
		getSheet().setRepeatingRows(arg0);
	}

	public void setRightToLeft(boolean arg0)
	{
		getSheet().setRightToLeft(arg0);
	}

	public void setRowBreak(int arg0)
	{
		getSheet().setRowBreak(arg0);
	}

	public void setRowGroupCollapsed(int arg0, boolean arg1)
	{
		getSheet().setRowGroupCollapsed(arg0, arg1);
	}

	public void setRowSumsBelow(boolean arg0)
	{
		getSheet().setRowSumsBelow(arg0);
	}

	public void setRowSumsRight(boolean arg0)
	{
		getSheet().setRowSumsRight(arg0);
	}

	public void setSelected(boolean arg0)
	{
		getSheet().setSelected(arg0);
	}

	public void setVerticallyCenter(boolean arg0)
	{
		getSheet().setVerticallyCenter(arg0);
	}

	public void setZoom(int arg0, int arg1)
	{
		getSheet().setZoom(arg0, arg1);
	}

	public void shiftRows(int arg0, int arg1, int arg2)
	{
		getSheet().shiftRows(arg0, arg1, arg2);
	}

	public void shiftRows(int arg0, int arg1, int arg2, boolean arg3, boolean arg4)
	{
		getSheet().shiftRows(arg0, arg1, arg2, arg3, arg4);
	}

	public void showInPane(int arg0, int arg1)
	{
		getSheet().showInPane(arg0, arg1);
	}

	public void showInPane(short arg0, short arg1)
	{
		getSheet().showInPane(arg0, arg1);
	}

	public void ungroupColumn(int arg0, int arg1)
	{
		getSheet().ungroupColumn(arg0, arg1);
	}

	public void ungroupRow(int arg0, int arg1)
	{
		getSheet().ungroupRow(arg0, arg1);
	}
}
