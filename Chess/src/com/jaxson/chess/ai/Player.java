package com.jaxson.chess.ai;

import com.jaxson.chess.board.IntBoard;
import com.jaxson.lib.geom.Point;

public abstract class Player
{
	protected static final int INFINITY = 10000000;
	protected static final int DEFAULT_DEPTH = 0;

	private int color, depth;
	private String name;

	public Player(int color)
	{
		this(color, "Generic Computer");
	}

	public Player(int color, String name)
	{
		this(color, DEFAULT_DEPTH, name);
	}

	public Player(int color, int depth, String name)
	{
		this.color = color;
		this.depth = depth;
		this.name = name;
	}

	/*
	 [java] TOTAL VALUE: 1
     [java] 23, 25, 24, 22, 21, 24, 00, 23,
     [java] 26, 26, 26, 26, 26, 26, 26, 26,
     [java] 00, 00, 00, 00, 00, 00, 00, 00,
     [java] 00, 00, 00, 00, 00, 00, 00, 25,
     [java] 00, 00, 00, 00, 00, 00, 00, 00,
     [java] 00, 00, 00, 00, 00, 00, 00, 00,
     [java] 16, 16, 16, 00, 16, 16, 16, 16,
     [java] 13, 15, 14, 12, 11, 14, 15, 13,
	 */

	public abstract int evaluateBoard(IntBoard board);

	public int getColor()
	{
		return color;
	}

	public int getDepth()
	{
		return depth;
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
