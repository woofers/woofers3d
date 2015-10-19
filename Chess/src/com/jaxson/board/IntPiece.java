package com.jaxson.board;

import com.jaxson.geom.Point;

public class IntPiece
{
	public int type, color, direction;
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

	public IntPiece(int type, int color, Point location, int direction)
	{
		this(type, color, location, direction, false);
	}

	public IntPiece(int type, int color, Point location, int direction, Boolean hasMoved)
	{
		this.type = type;
		this.color = color;
		this.location = location;
		this.direction = direction;
		this.hasMoved = hasMoved;
	}

	public Boolean isEmpty()
	{
		return type == 0;
	}

	public Boolean isEnemey(int color)
	{
		return !isFriendly(color) && !isEmpty();
	}

	public Boolean isFriendly(int color)
	{
		return this.color == color;
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
