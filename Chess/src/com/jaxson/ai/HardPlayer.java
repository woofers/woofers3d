package com.jaxson.ai;

import com.jaxson.board.IntBoard;
import com.jaxson.geom.Point;

public class HardPlayer extends Player
{
	public HardPlayer(int color)
	{
		super(color, "Hard AI");
	}

	public int evaluateBoard(IntBoard board)
	{
		return 0;
	}

	public void move(Point location)
	{

	}
}
