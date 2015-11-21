package com.jaxson.chess.board;

import com.jaxson.chess.ui.Piece;
import com.jaxson.lib.geom.Point;

public class IntPiece
{
	public int type, color, direction, turn;
	public int passingIndex, boardHeight;
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
		IntPiece newIntPiece = new IntPiece(type, color, location.clone(), direction, turn);
		newIntPiece.passingIndex = passingIndex;
		return newIntPiece;
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

	public Boolean isPromotable()
	{
		if (type == Piece.PAWN)
		{
			if (location.y == 0)
			{
				return color == Piece.BLACK;
			}
			if (location.y == boardHeight)
			{
				return color == Piece.WHITE;
			}
		}
		return false;
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
