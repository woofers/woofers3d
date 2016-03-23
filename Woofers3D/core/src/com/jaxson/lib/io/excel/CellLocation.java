package com.jaxson.lib.io.excel;

import com.jaxson.lib.math.geom.Point;
import com.jaxson.lib.util.exceptions.NegativeValueException;
import com.jaxson.lib.util.exceptions.NullValueException;

public class CellLocation
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

	public CellLocation(MyCell cell)
	{
		this(cell.getColumnIndex(), cell.getRowIndex());
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
		if (column == null || column.isEmpty()) throw new NullValueException("column");
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
		cell = cell.trim();
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
