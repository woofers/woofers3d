package com.jaxson.board;

public class IntPiece
{
	public int color, type, direction;
	public Point location;
	public Boolean hasMoved;

	public IntPiece()
	{
		this(new Point());
	}

	public IntPiece(Point location)
	{
		this(0, 0, location, 0);
	}

	public IntPiece(int color, int type, Point location, int direction)
	{
		this(color, type, location, direction, false);
	}

	public IntPiece(int color, int type, Point location, int direction, Boolean hasMoved)
	{
		this.color = color;
		this.type = type;
		this.location = location;
		this.direction = direction;
		this.hasMoved = hasMoved;
	}

	public Boolean isFriendly(int color)
	{
		return this.color == color;
	}

	public Boolean isEmpty()
	{
		return type == 0;
	}

	public int toInt()
	{
		return Integer.parseInt(toString());
	}

	@Override
	public String toString()
	{
		return Integer.toString(color) + Integer.toString(type);
	}
}