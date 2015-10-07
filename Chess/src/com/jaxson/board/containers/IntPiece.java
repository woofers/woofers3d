package com.jaxson.board.containers;

public class IntPiece
{
	public int color;
	public int type;

	public IntPiece()
	{
		this(0, 0);
	}

	public IntPiece(int color, int type)
	{
		this.color = color;
		this.type = type;
	}
}