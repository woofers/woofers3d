package com.jaxson.ai;

import com.jaxson.board.IntBoard;
import com.jaxson.geom.Point;

public class EasyPlayer extends Player
{
	public EasyPlayer(int color)
	{
		super(color, "Easy AI");
	}

	public int evaluateBoard(IntBoard board)
	{
		return 0;
	}

	public void move(Point location)
	{

	}
}
