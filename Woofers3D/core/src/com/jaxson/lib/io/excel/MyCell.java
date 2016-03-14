package com.jaxson.lib.io.excel;

import com.jaxson.lib.math.geom.Point;
import com.jaxson.lib.util.exceptions.NegativeValueException;
import com.jaxson.lib.util.exceptions.NullValueException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.util.CellRangeAddress;

public class MyCell
{
	public static class CellLocation
	{
		private static final int ALPHABET_START = 64;
		private static final int ALPHABET_MAX = 26;
		private static final char CHAR_MAX = '9';
		private static final char CHAR_MIN = '0';

		private int x;
		private int y;

		public CellLocation(int x, int y)
		{
			this.x = x;
			this.y = y;
			if (x < 0 || y < 0) throw new CellOutOfBoundsException(this);
		}

		private CellLocation(Point point)
		{
			this(point.getX(), point.getY());
		}

		public CellLocation(String cell)
		{
			this(getPoint(cell));
		}

		public Point getPoint()
		{
			return new Point(getX(), getY());
		}

		public String getString()
		{
			return intToChar(getX()) + (getY() + 1);
		}

		public int getX()
		{
			return x;
		}

		public int getY()
		{
			return y;
		}

		public CellLocation nextColumn()
		{
			return nextColumn(1);
		}

		public CellLocation nextColumn(int amount)
		{
			checkAmount(amount);
			return shift(amount, 0);
		}

		public CellLocation nextRow()
		{
			return nextRow(1);
		}

		public CellLocation nextRow(int amount)
		{
			checkAmount(amount);
			return shift(0, amount);
		}

		public CellLocation prevColumn()
		{
			return prevColumn(1);
		}

		public CellLocation prevColumn(int amount)
		{
			checkAmount(amount);
			return shift(-amount, 0);
		}

		public CellLocation prevRow()
		{
			return prevRow(1);
		}

		public CellLocation prevRow(int amount)
		{
			checkAmount(amount);
			return shift(0, -amount);
		}

		public CellLocation set(int x, int y)
		{
			return new CellLocation(x, y);
		}

		public CellLocation set(String cell)
		{
			return new CellLocation(cell);
		}

		public CellLocation setX(int x)
		{
			return set(x, y);
		}

		public CellLocation setY(int y)
		{
			return set(x, y);
		}

		public CellLocation shift(int amountX, int amountY)
		{
			if (amountX == 0 && amountY == 0) return this;
			return new CellLocation(getX() + amountX, getY() + amountY);
		}

		@Override
		public String toString()
		{
			return getString();
		}

		private static void checkAmount(int amount)
		{
			if (amount < 0) throw new NegativeValueException("amount");
		}

		public static int columnToInt(String column)
		{
			if (column == null || column.equals("")) throw new NullValueException("column");
			char[] columnArray = column.toUpperCase().toCharArray();
			int sum = 0;
			for (int i = 0; i < columnArray.length; i ++)
			{
				sum *= ALPHABET_MAX;
				sum += new Integer(columnArray[i] - 'A' + 1);
			}

			return sum - 1;
		}

		private static Point getPoint(String cell)
		{
			char[] characters = cell.toUpperCase().toCharArray();
			String numbers = "";
			String letters = "";
			for (char character: characters)
			{
				if (isNumber(character))
				{
					numbers += character;
				}
				else
				{
					letters += character;
				}
			}
			return new Point(columnToInt(letters), new Integer(numbers).intValue() - 1);
		}

		private static String intToChar(int columnNumber)
		{
			String columnName = "";
			int dividend = columnNumber + 1;
			int modulo = 0;
			int newCharValue = 0;
			while (dividend > 0)
			{
				modulo = (dividend - 1) % ALPHABET_MAX;
				newCharValue = 'A' + modulo;
				columnName = new String(new char[]{ (char) newCharValue }) + columnName;
				dividend = (dividend - modulo) / ALPHABET_MAX;
			}
			return columnName;
		}

		private static boolean isNumber(char character)
		{
			return character >= CHAR_MIN && character <= CHAR_MAX;
		}
	}

	public static class CellOutOfBoundsException extends IndexOutOfBoundsException
	{
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

	public CellRangeAddress getArrayFormulaRange()
	{
		return getCell().getArrayFormulaRange();
	}

	public boolean getBooleanCellValue()
	{
		return getCell().getBooleanCellValue();
	}

	public int getCachedFormulaResultType()
	{
		return getCell().getCachedFormulaResultType();
	}

	protected Cell getCell()
	{
		return cell;
	}

	public Comment getCellComment()
	{
		return getCell().getCellComment();
	}

	public String getCellFormula()
	{
		return getCell().getCellFormula();
	}

	public CellStyle getCellStyle()
	{
		return getCell().getCellStyle();
	}

	public int getCellType()
	{
		return getCell().getCellType();
	}

	public MyColumn getColumn()
	{
		return getSheet().getColumn(getColumnIndex());
	}

	public int getColumnIndex()
	{
		return getCell().getColumnIndex();
	}

	public Date getDateCellValue()
	{
		return getCell().getDateCellValue();
	}

	public byte getErrorCellValue()
	{
		return getCell().getErrorCellValue();
	}

	public Hyperlink getHyperlink()
	{
		return getCell().getHyperlink();
	}

	public CellLocation getLocation()
	{
		return new CellLocation(getColumnIndex(), getRowIndex());
	}

	public double getNumericCellValue()
	{
		return getCell().getNumericCellValue();
	}

	public RichTextString getRichStringCellValue()
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

	public String getStringCellValue()
	{
		return getCell().getStringCellValue();
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
				return new Boolean(getCell().getBooleanCellValue()).toString();
			case TYPE_FORMULA:
				return getCell().getCellFormula();
			case TYPE_NUMERIC:
				return new Double(getCell().getNumericCellValue()).toString();
			case TYPE_STRING:
				return getCell().getStringCellValue();
		}
		return new String(new byte[]{ getCell().getErrorCellValue() }, StandardCharsets.UTF_8);
	}

	public boolean isPartOfArrayFormulaGroup()
	{
		return getCell().isPartOfArrayFormulaGroup();
	}

	public void removeCellComment()
	{
		getCell().removeCellComment();
	}

	public void removeHyperlink()
	{
		getCell().removeHyperlink();
	}

	public void setAsActiveCell()
	{
		getCell().setAsActiveCell();
	}

	public void setCellComment(Comment comment)
	{
		getCell().setCellComment(comment);
	}

	public void setCellErrorValue(byte error)
	{
		getCell().setCellErrorValue(error);
	}

	public void setCellFormula(String formula) throws FormulaParseException
	{
		getCell().setCellFormula(formula);
	}

	public void setCellStyle(CellStyle style)
	{
		getCell().setCellStyle(style);
	}

	public void setCellType(int type)
	{
		getCell().setCellType(type);
	}

	public void setCellValue(boolean value)
	{
		getCell().setCellValue(value);
	}

	public void setCellValue(Calendar calendar)
	{
		getCell().setCellValue(calendar);
	}

	public void setCellValue(Date date)
	{
		getCell().setCellValue(date);
	}

	public void setCellValue(double value)
	{
		getCell().setCellValue(value);
	}

	public void setCellValue(RichTextString text)
	{
		getCell().setCellValue(text);
	}

	public void setHyperlink(Hyperlink link)
	{
		getCell().setHyperlink(link);
	}

	public void setValue(String value)
	{
		getCell().setCellValue(value);
	}
}
