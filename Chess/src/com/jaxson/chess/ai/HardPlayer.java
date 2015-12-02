package com.jaxson.chess.ai;

import com.jaxson.chess.board.IntBoard;
import com.jaxson.chess.board.IntPiece;
import com.jaxson.chess.ui.Piece;
import com.jaxson.lib.geom.Point;
import com.jaxson.lib.util.MyArrayList;

public class HardPlayer extends Player
{
	private static final int DEPTH = 2;

	private static final int KING   = INFINITY;
	private static final int QUEEN  = 9;
	private static final int ROOK   = 5;
	private static final int BISHOP = 3;
	private static final int KNIGHT = 3;
	private static final int PAWN   = 1;

	public HardPlayer(int color)
	{
		super(color, DEPTH, "Hard AI");
	}

	public int evaluateBoard(IntBoard board)
	{
		MyArrayList<IntPiece> pieces = board.getPieces();
		int color, totalValue, value;
		totalValue = 0;
		color = Piece.getOppositeColor(board.getColor());
		for (IntPiece piece: pieces)
		{
			if (piece.isFriendly(color))
			{
				totalValue += getValue(piece);
			}
			else
			{
				totalValue -= getValue(piece);
			}
		}
		//System.out.println("TOTAL VALUE: " + totalValue);
		//board.print();
		//System.out.println("----end value---");
		return totalValue;
	}


	private int getValue(IntPiece piece)
	{
		switch (piece.type)
		{
			case Piece.KING:
				return KING;
			case Piece.QUEEN:
				return QUEEN;
			case Piece.ROOK:
				return ROOK;
			case Piece.BISHOP:
				return BISHOP;
			case Piece.KNIGHT:
				return KNIGHT;
			case Piece.PAWN:
				return PAWN;
		}
		return 0;
	}

	public void move(Point location)
	{

	}
}
