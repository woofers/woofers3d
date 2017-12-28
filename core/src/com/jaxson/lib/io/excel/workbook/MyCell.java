package com.jaxson.lib.io.excel.workbook;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.util.CellRangeAddress;

public class MyCell
{
    public static final int TYPE_BLANK = Cell.CELL_TYPE_BLANK;
    public static final int TYPE_BOOLEAN = Cell.CELL_TYPE_BOOLEAN;
    public static final int TYPE_FORMULA = Cell.CELL_TYPE_FORMULA;
    public static final int TYPE_NUMERIC = Cell.CELL_TYPE_NUMERIC;
    public static final int TYPE_STRING = Cell.CELL_TYPE_STRING;

    private Cell cell;

    public MyCell(Cell cell)
    {
        this.cell = cell;
    }

    public boolean booleanValue()
    {
        return cell().getBooleanCellValue();
    }

    public int cachedFormulaResultType()
    {
        return cell().getCachedFormulaResultType();
    }

    protected Cell cell()
    {
        return cell;
    }

    public MyColumn column()
    {
        return sheet().column(columnIndex());
    }

    public int columnIndex()
    {
        return cell().getColumnIndex();
    }

    public Comment comment()
    {
        return cell().getCellComment();
    }

    public Date dateValue()
    {
        return cell().getDateCellValue();
    }

    public MyCell down()
    {
        return sheet().cell(location().nextRow());
    }

    public MyCell down(int amount)
    {
        return sheet().cell(location().nextRow(amount));
    }

    public boolean equalsValue(MyCell cell)
    {
        return equalsValue(cell.value());
    }

    public boolean equalsValue(String value)
    {
        return value().equals(value.toLowerCase());
    }

    public byte errorValue()
    {
        return cell().getErrorCellValue();
    }

    public String formula()
    {
        return cell().getCellFormula();
    }

    public CellRangeAddress formulaRange()
    {
        return cell().getArrayFormulaRange();
    }

    public Hyperlink hyperlink()
    {
        return cell().getHyperlink();
    }

    public boolean isPartOfArrayFormulaGroup()
    {
        return cell().isPartOfArrayFormulaGroup();
    }

    public MyCell left()
    {
        return sheet().cell(location().prevColumn());
    }

    public MyCell left(int amount)
    {
        return sheet().cell(location().prevColumn(amount));
    }

    public CellLocation location()
    {
        return new CellLocation(this);
    }

    public double numericValue()
    {
        return cell().getNumericCellValue();
    }

    public void removeComment()
    {
        cell().removeCellComment();
    }

    public void removeHyperlink()
    {
        cell().removeHyperlink();
    }

    public RichTextString richStringValue()
    {
        return cell().getRichStringCellValue();
    }

    public MyCell right()
    {
        return sheet().cell(location().nextColumn());
    }

    public MyCell right(int amount)
    {
        return sheet().cell(location().nextColumn(amount));
    }

    public MyRow row()
    {
        return new MyRow(cell().getRow());
    }

    public int rowIndex()
    {
        return cell().getRowIndex();
    }

    public void setAsActive()
    {
        cell().setAsActiveCell();
    }

    public void setComment(Comment comment)
    {
        cell().setCellComment(comment);
    }

    public void setErrorValue(byte error)
    {
        cell().setCellErrorValue(error);
    }

    public void setFormula(String formula) throws FormulaParseException
    {
        cell().setCellFormula(formula);
    }

    public void setHyperlink(Hyperlink link)
    {
        cell().setHyperlink(link);
    }

    public void setStyle(MyCellStyle style)
    {
        cell().setCellStyle(style.style());
    }

    public void setType(int type)
    {
        cell().setCellType(type);
    }

    public void setValue(boolean value)
    {
        cell().setCellValue(value);
    }

    public void setValue(Calendar calendar)
    {
        cell().setCellValue(calendar);
    }

    public void setValue(Date date)
    {
        cell().setCellValue(date);
    }

    public void setValue(double value)
    {
        cell().setCellValue(value);
    }

    public void setValue(RichTextString text)
    {
        cell().setCellValue(text);
    }

    public void setValue(String value)
    {
        cell().setCellValue(value);
    }

    public MySheet sheet()
    {
        return new MySheet(cell().getSheet());
    }

    public String stringValue()
    {
        return cell().getStringCellValue();
    }

    public MyCellStyle style()
    {
        return new MyCellStyle(cell().getCellStyle());
    }

    public int type()
    {
        return cell().getCellType();
    }

    public MyCell up()
    {
        return sheet().cell(location().prevRow());
    }

    public MyCell up(int amount)
    {
        return sheet().cell(location().prevRow(amount));
    }

    public String value()
    {
        switch (type())
        {
            case TYPE_BLANK:
                return "";
            case TYPE_BOOLEAN:
                return new Boolean(booleanValue()).toString();
            case TYPE_FORMULA:
                return formula();
            case TYPE_NUMERIC:
                return new Double(numericValue()).toString();
            case TYPE_STRING:
                return stringValue();
        }
        return new String(new byte[]{ cell().getErrorCellValue() },
                StandardCharsets.UTF_8);
    }

    public int x()
    {
        return location().x();
    }

    public int y()
    {
        return location().y();
    }
}
