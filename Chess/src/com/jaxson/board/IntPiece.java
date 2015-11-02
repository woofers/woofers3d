package com.jaxson.board;

import com.jaxson.geom.Point;
import com.jaxson.ui.board.Piece;

public class IntPiece
{
	public int type, color, direction, turn;
	public int passingIndex;
	public Point location;

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
		this(type, color, location, direction, 0);
	}

	public IntPiece(int type, int color, Point location, int direction, int turn)
	{
		this.type = type;
		this.color = color;
		this.location = location;
		this.direction = direction;
		this.turn = turn;
		this.passingIndex = -1;
	}

	public Boolean canPass(int turn)
	{
		return type == Piece.PAWN && passingIndex == turn;
	}

	public IntPiece clone()
	{
		return new IntPiece(type, color, location, direction, turn);
	}

	public Boolean hasMoved()
	{
		return turn != 0;
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
