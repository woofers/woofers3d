package com.jaxson.lib.io.excel.workbook;

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
import com.jaxson.lib.util.MyArrayList;

public class MySheet implements Iterable<MyRow>
{
    public static final int NO_ROWS = -1;

    private Sheet sheet;

    public MySheet(Sheet sheet)
    {
        this.sheet = sheet;
    }

    public int addMergedRegion(CellRangeAddress region)
    {
        return sheet().addMergedRegion(region);
    }

    public void addValidationData(DataValidation dataValidation)
    {
        sheet().addValidationData(dataValidation);
    }

    public int amountOfMergedRegions()
    {
        return sheet().getNumMergedRegions();
    }

    public boolean autobreaks()
    {
        return sheet().getAutobreaks();
    }

    public void autoSizeColumn(int column)
    {
        sheet().autoSizeColumn(column);
    }

    public void autoSizeColumn(int column, boolean mergedCells)
    {
        sheet().autoSizeColumn(column, mergedCells);
    }

    public MyCell cell(CellLocation location) throws CellOutOfBoundsException
    {
        int y = location.y() + 1;
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
        return row.cell(location);
    }

    public MyCell cell(int x, int y)
    {
        return cell(new CellLocation(x, y));
    }

    public MyCell cell(String cell)
    {
        return cell(new CellLocation(cell));
    }

    public Comment cellComment(CellLocation location)
    {
        return cellComment(location.x(), location.y());
    }

    public Comment cellComment(int column, int row)
    {
        return sheet().getCellComment(row, column);
    }

    @Override
    public MySheet clone()
    {
        return new MySheet(workbook().cloneSheet(index()));
    }

    public MyColumn column(CellLocation location)
    {
        return column(location.x());
    }

    public MyColumn column(int column)
    {
        MyArrayList<MyCell> cells = new MyArrayList<>();
        MyCell cell = topRow().cell(column);
        CellLocation location = cell.location();
        do
        {
            cells.add(cell);
            location = location.nextRow();
            try
            {
                cell = cell(location);
            }
            catch (Exception ex)
            {
                cell = null;
            }
        }
        while (cell != null);
        return new MyColumn(this, cells);
    }

    public int[] columnBreaks()
    {
        return sheet().getColumnBreaks();
    }

    public int columnOutlineLevel(int column)
    {
        return sheet().getColumnOutlineLevel(column);
    }

    public Iterable<MyColumn> columns()
    {
        MyArrayList<MyColumn> columns = new MyArrayList<>();
        MyRow topRow = iterator().next();
        CellLocation location = null;
        MyCell cellRow = null;
        for (MyCell cell: topRow)
        {
            MyArrayList<MyCell> cells = new MyArrayList<>();
            location = cell.location();
            cellRow = cell(location);
            do
            {
                cells.add(cellRow);
                location = location.nextRow();
                try
                {
                    cellRow = cell(location);
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

    public MyCellStyle columnStyle(int column)
    {
        return new MyCellStyle(sheet().getColumnStyle(column));
    }

    public int columnWidth(int column)
    {
        return sheet().getColumnWidth(column);
    }

    public float columnWidthInPixels(int column)
    {
        return sheet().getColumnWidthInPixels(column);
    }

    public Drawing createDrawingPatriarch()
    {
        return sheet().createDrawingPatriarch();
    }

    public void createFreezePane(int columnSplit, int rowSplit)
    {
        sheet().createFreezePane(columnSplit, rowSplit);
    }

    public void createFreezePane(int columnSplit,
            int rowSplit,
            int leftmostColumn,
            int topRow)
    {
        sheet().createFreezePane(
                columnSplit, rowSplit, leftmostColumn,
                topRow);
    }

    public MyRow createRow()
    {
        return createRow(lastRowIndex() + 1);
    }

    public MyRow createRow(int index)
    {
        return new MyRow(sheet().createRow(index));
    }

    public void createSplitPane(int xSplit,
            int ySplit,
            int leftMostColumn,
            int topRow,
            int activePane)
    {
        sheet().createSplitPane(
                xSplit, ySplit, leftMostColumn, topRow,
                activePane);
    }

    public DataValidationHelper dataValidationHelper()
    {
        return sheet().getDataValidationHelper();
    }

    public List<? extends DataValidation> dataValidations()
    {
        return sheet().getDataValidations();
    }

    public int defaultColumnWidth()
    {
        return sheet().getDefaultColumnWidth();
    }

    public short defaultRowHeight()
    {
        return sheet().getDefaultRowHeight();
    }

    public float defaultRowHeightInPoints()
    {
        return sheet().getDefaultRowHeightInPoints();
    }

    public boolean displaysFormulas()
    {
        return sheet().isDisplayFormulas();
    }

    public boolean displaysGridlines()
    {
        return sheet().isDisplayGridlines();
    }

    public boolean displaysGuts()
    {
        return sheet().getDisplayGuts();
    }

    public boolean displaysRowColHeadings()
    {
        return sheet().isDisplayRowColHeadings();
    }

    public boolean displaysZeros()
    {
        return sheet().isDisplayZeros();
    }

    public MyRow firstRow()
    {
        return row(firstRowIndex());
    }

    public int firstRowIndex()
    {
        return sheet().getFirstRowNum();
    }

    public boolean fitsToPage()
    {
        return sheet().getFitToPage();
    }

    public Footer footer()
    {
        return sheet().getFooter();
    }

    public boolean forcesFormulaRecalculation()
    {
        return sheet().getForceFormulaRecalculation();
    }

    public SheetConditionalFormatting formatting()
    {
        return sheet().getSheetConditionalFormatting();
    }

    public void groupColumn(int startColumn, int endColumn)
    {
        sheet().groupColumn(startColumn, endColumn);
    }

    public void groupRow(int startRow, int endStart)
    {
        sheet().groupRow(startRow, endStart);
    }

    public boolean hasRow()
    {
        return firstRow().row() != null;
    }

    public Header header()
    {
        return sheet().getHeader();
    }

    public int index()
    {
        return workbook().sheetIndex(this);
    }

    public boolean isColumnBroken(int column)
    {
        return sheet().isColumnBroken(column);
    }

    public boolean isColumnHidden(int column)
    {
        return sheet().isColumnHidden(column);
    }

    public boolean isHorizontallyCenter()
    {
        return sheet().getHorizontallyCenter();
    }

    public boolean isProtected()
    {
        return sheet().getProtect();
    }

    public boolean isRightToLeft()
    {
        return sheet().isRightToLeft();
    }

    public boolean isRowBroken(int row)
    {
        return sheet().isRowBroken(row);
    }

    public boolean isScenarioProtected()
    {
        return sheet().getScenarioProtect();
    }

    public boolean isSelected()
    {
        return sheet().isSelected();
    }

    public boolean isVerticallyCenter()
    {
        return sheet().getVerticallyCenter();
    }

    @Override
    public Iterator<MyRow> iterator()
    {
        return rows().iterator();
    }

    public MyRow lastRow()
    {
        return row(lastRowIndex());
    }

    public int lastRowIndex()
    {
        if (!hasRow()) return NO_ROWS;
        return sheet().getLastRowNum();
    }

    public short leftColumn()
    {
        return sheet().getLeftCol();
    }

    public double margin(short margin)
    {
        return sheet().getMargin(margin);
    }

    public CellRangeAddress mergedRegion(int index)
    {
        return sheet().getMergedRegion(index);
    }

    public String name()
    {
        return sheet().getSheetName();
    }

    public PaneInformation paneInformation()
    {
        return sheet().getPaneInformation();
    }

    public int physicalNumberOfRows()
    {
        return sheet().getPhysicalNumberOfRows();
    }

    public PrintSetup printSetup()
    {
        return sheet().getPrintSetup();
    }

    public boolean printsGridlines()
    {
        return sheet().isPrintGridlines();
    }

    public void protectSheet(String password)
    {
        sheet().protectSheet(password);
    }

    public CellRange<? extends Cell> removeArrayFormula(Cell cell)
    {
        return sheet().removeArrayFormula(cell);
    }

    public void removeColumnBreak(int column)
    {
        sheet().removeColumnBreak(column);
    }

    public void removeMergedRegion(int index)
    {
        sheet().removeMergedRegion(index);
    }

    public void removeRow(int row)
    {
        removeRow(row(row));
    }

    public void removeRow(MyRow row)
    {
        sheet().removeRow(row.row());
    }

    public void removeRowBreak(int row)
    {
        sheet().removeRowBreak(row);
    }

    public CellRangeAddress repeatingColumns()
    {
        return sheet().getRepeatingColumns();
    }

    public CellRangeAddress repeatingRows()
    {
        return sheet().getRepeatingRows();
    }

    public MyRow row(int index)
    {
        return new MyRow(sheet().getRow(index));
    }

    public int[] rowBreaks()
    {
        return sheet().getRowBreaks();
    }

    public Iterable<MyRow> rows()
    {
        MyArrayList<MyRow> rows = new MyArrayList<>();
        for (Row row: sheet())
        {
            rows.add(new MyRow(row));
        }
        return rows;
    }

    public boolean rowSumsAreBelow()
    {
        return sheet().getRowSumsBelow();
    }

    public boolean rowSumsAreRight()
    {
        return sheet().getRowSumsRight();
    }

    public CellRange<? extends Cell> setArrayFormula(String formula,
            CellRangeAddress range)
    {
        return sheet().setArrayFormula(formula, range);
    }

    public void setAutobreaks(boolean autobreaks)
    {
        sheet().setAutobreaks(autobreaks);
    }

    public AutoFilter setAutoFilter(CellRangeAddress range)
    {
        return sheet().setAutoFilter(range);
    }

    public void setColumnBreak(int arg0)
    {
        sheet().setColumnBreak(arg0);
    }

    public void setColumnGroupCollapsed(int arg0, boolean arg1)
    {
        sheet().setColumnGroupCollapsed(arg0, arg1);
    }

    public void setColumnHidden(int arg0, boolean arg1)
    {
        sheet().setColumnHidden(arg0, arg1);
    }

    public void setColumnWidth(int arg0, int arg1)
    {
        sheet().setColumnWidth(arg0, arg1);
    }

    public void setDefaultColumnStyle(int arg0, CellStyle arg1)
    {
        sheet().setDefaultColumnStyle(arg0, arg1);
    }

    public void setDefaultColumnWidth(int arg0)
    {
        sheet().setDefaultColumnWidth(arg0);
    }

    public void setDefaultRowHeight(short arg0)
    {
        sheet().setDefaultRowHeight(arg0);
    }

    public void setDefaultRowHeightInPoints(float arg0)
    {
        sheet().setDefaultRowHeightInPoints(arg0);
    }

    public void setDisplayFormulas(boolean arg0)
    {
        sheet().setDisplayFormulas(arg0);
    }

    public void setDisplayGridlines(boolean arg0)
    {
        sheet().setDisplayGridlines(arg0);
    }

    public void setDisplayGuts(boolean arg0)
    {
        sheet().setDisplayGuts(arg0);
    }

    public void setDisplayRowColHeadings(boolean arg0)
    {
        sheet().setDisplayRowColHeadings(arg0);
    }

    public void setDisplayZeros(boolean arg0)
    {
        sheet().setDisplayZeros(arg0);
    }

    public void setFitToPage(boolean arg0)
    {
        sheet().setFitToPage(arg0);
    }

    public void setForceFormulaRecalculation(boolean arg0)
    {
        sheet().setForceFormulaRecalculation(arg0);
    }

    public void setHorizontallyCenter(boolean arg0)
    {
        sheet().setHorizontallyCenter(arg0);
    }

    public void setMargin(short arg0, double arg1)
    {
        sheet().setMargin(arg0, arg1);
    }

    public void setPrintGridlines(boolean arg0)
    {
        sheet().setPrintGridlines(arg0);
    }

    public void setRepeatingColumns(CellRangeAddress arg0)
    {
        sheet().setRepeatingColumns(arg0);
    }

    public void setRepeatingRows(CellRangeAddress arg0)
    {
        sheet().setRepeatingRows(arg0);
    }

    public void setRightToLeft(boolean arg0)
    {
        sheet().setRightToLeft(arg0);
    }

    public void setRowBreak(int arg0)
    {
        sheet().setRowBreak(arg0);
    }

    public void setRowGroupCollapsed(int arg0, boolean arg1)
    {
        sheet().setRowGroupCollapsed(arg0, arg1);
    }

    public void setRowSumsBelow(boolean arg0)
    {
        sheet().setRowSumsBelow(arg0);
    }

    public void setRowSumsRight(boolean arg0)
    {
        sheet().setRowSumsRight(arg0);
    }

    public void setSelected(boolean arg0)
    {
        sheet().setSelected(arg0);
    }

    public void setVerticallyCenter(boolean arg0)
    {
        sheet().setVerticallyCenter(arg0);
    }

    public void setZoom(int arg0, int arg1)
    {
        sheet().setZoom(arg0, arg1);
    }

    protected Sheet sheet()
    {
        return sheet;
    }

    public void shiftRows(int arg0, int arg1, int arg2)
    {
        sheet().shiftRows(arg0, arg1, arg2);
    }

    public void shiftRows(int arg0,
            int arg1,
            int arg2,
            boolean arg3,
            boolean arg4)
    {
        sheet().shiftRows(arg0, arg1, arg2, arg3, arg4);
    }

    public void showInPane(int arg0, int arg1)
    {
        sheet().showInPane(arg0, arg1);
    }

    public MyRow topRow()
    {
        return iterator().next();
    }

    public void ungroupColumn(int arg0, int arg1)
    {
        sheet().ungroupColumn(arg0, arg1);
    }

    public void ungroupRow(int arg0, int arg1)
    {
        sheet().ungroupRow(arg0, arg1);
    }

    public MyWorkbook workbook()
    {
        return new MyWorkbook(sheet().getWorkbook());
    }
}
