package com.jaxson.chess.ai;

import com.jaxson.chess.board.IntBoard;
import com.jaxson.lib.geom.Point;

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
