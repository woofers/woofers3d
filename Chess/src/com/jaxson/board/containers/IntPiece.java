package com.jaxson.board.containers;

public class IntPiece
{
	public int color;
	public int type;
	public Point location;
	public Boolean hasMoved;

	public IntPiece()
	{
		this(0, 0, new Point());
	}

	public IntPiece(Point location)
	{
		this(0, 0, location);
	}

	public IntPiece(int color, int type, Point location)
	{
		this(color, type, location, false);
	}

	public IntPiece(int color, int type, Point location, Boolean hasMoved)
	{
		this.color = color;
		this.type = type;
		this.location = location;
		this.hasMoved = hasMoved;
	}

	public Boolean isFriendly(int color)
	{
		return this.color == color;
	}

	public int toInt()
	{
		return Integer.parseInt(toString());
	}

	public String toString()
	{
		return Integer.toString(color) + Integer.toString(type);
	}
}
