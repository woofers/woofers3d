package com.jaxson.ai;

import com.jaxson.board.IntBoard;
import com.jaxson.geom.Point;

public abstract class Player
{
	protected static final int DEPTH = 4;
	protected static final int INFINITY = 10000000;

	private int color;
	private String name;

	public Player(int color)
	{
		this(color, "Generic Computer");
	}

	public Player(int color, String name)
	{
		this.color = color;
		this.name = name;
	}

	public abstract int evaluateBoard(IntBoard board);

	public int getColor()
	{
		return color;
	}

	public int getDepth()
	{
		return DEPTH;
	}

	public String getName()
	{
		return name;
	}

	public abstract void move(Point location);

	@Override
	public String toString()
	{
		return name + "playing as " + color;
	}

}
