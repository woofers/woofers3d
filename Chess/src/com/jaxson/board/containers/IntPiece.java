package com.jaxson.board.containers;

public class IntPiece
{
	public int color;
	public int type;
	public Point location;

	public IntPiece()
	{
		this(0, 0, new Point());
	}

	public IntPiece(int color, int type, Point location)
	{
		this.color = color;
		this.type = type;
		this.location = location;
	}

	public int toInt()
	{
		return Integer.parseInt(Integer.toString(color) + Integer.toString(type));
	}
}