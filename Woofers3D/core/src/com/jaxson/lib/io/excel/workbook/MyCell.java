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

	public boolean equalsValue(MyCell cell)
	{
		return equalsValue(cell.getValue());
	}

	public boolean equalsValue(String value)
	{
		return getValue().equals(value);
	}

	public CellRangeAddress getArrayFormulaRange()
	{
		return getCell().getArrayFormulaRange();
	}

	public boolean getBooleanValue()
	{
		return getCell().getBooleanCellValue();
	}

	public int getCachedFormulaResultType()
	{
		return getCell().getCachedFormulaResultType();
	}

	public MyColumn getColumn()
	{
		return getSheet().getColumn(getColumnIndex());
	}

	public int getColumnIndex()
	{
		return getCell().getColumnIndex();
	}

	public Comment getComment()
	{
		return getCell().getCellComment();
	}

	public Date getDateValue()
	{
		return getCell().getDateCellValue();
	}

	public byte getErrorValue()
	{
		return getCell().getErrorCellValue();
	}

	public String getFormula()
	{
		return getCell().getCellFormula();
	}

	public Hyperlink getHyperlink()
	{
		return getCell().getHyperlink();
	}

	public CellLocation getLocation()
	{
		return new CellLocation(this);
	}

	public double getNumericValue()
	{
		return getCell().getNumericCellValue();
	}

	public RichTextString getRichStringValue()
	{
		return getCell().getRichStringCellValue();
	}

	public MyRow getRow()
	{
		return new MyRow(getCell().getRow());
	}

	public int getRowIndex()
	{
		return getCell().getRowIndex();
	}

	public MySheet getSheet()
	{
		return new MySheet(getCell().getSheet());
	}

	public String getStringValue()
	{
		return getCell().getStringCellValue();
	}

	public MyCellStyle getStyle()
	{
		return new MyCellStyle(getCell().getCellStyle());
	}

	public int getType()
	{
		return getCell().getCellType();
	}

	public String getValue()
	{
		switch (getType())
		{
			case TYPE_BLANK:
				return "";
			case TYPE_BOOLEAN:
				return new Boolean(getBooleanValue()).toString();
			case TYPE_FORMULA:
				return getFormula();
			case TYPE_NUMERIC:
				return new Double(getNumericValue()).toString();
			case TYPE_STRING:
				return getStringValue();
		}
		return new String(new byte[]{ getCell().getErrorCellValue() }, StandardCharsets.UTF_8);
	}

	public boolean isPartOfArrayFormulaGroup()
	{
		return getCell().isPartOfArrayFormulaGroup();
	}

	public void removeComment()
	{
		getCell().removeCellComment();
	}

	public void removeHyperlink()
	{
		getCell().removeHyperlink();
	}

	public void setAsActive()
	{
		getCell().setAsActiveCell();
	}

	public void setComment(Comment comment)
	{
		getCell().setCellComment(comment);
	}

	public void setErrorValue(byte error)
	{
		getCell().setCellErrorValue(error);
	}

	public void setFormula(String formula) throws FormulaParseException
	{
		getCell().setCellFormula(formula);
	}

	public void setHyperlink(Hyperlink link)
	{
		getCell().setHyperlink(link);
	}

	public void setStyle(MyCellStyle style)
	{
		getCell().setCellStyle(style.getStyle());
	}

	public void setType(int type)
	{
		getCell().setCellType(type);
	}

	public void setValue(boolean value)
	{
		getCell().setCellValue(value);
	}

	public void setValue(Calendar calendar)
	{
		getCell().setCellValue(calendar);
	}

	public void setValue(Date date)
	{
		getCell().setCellValue(date);
	}

	public void setValue(double value)
	{
		getCell().setCellValue(value);
	}

	public void setValue(RichTextString text)
	{
		getCell().setCellValue(text);
	}

	public void setValue(String value)
	{
		getCell().setCellValue(value);
	}

	protected Cell getCell()
	{
		return cell;
	}
}
