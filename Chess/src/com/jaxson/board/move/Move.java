package com.jaxson.board.move;

import java.util.ArrayList;

import com.jaxson.board.IntBoard;
import com.jaxson.board.IntPiece;
import com.jaxson.ui.board.Board;

public class Move
{
	private ArrayList<PieceMove> pieceMoves;

	public Move(IntPiece spot)
	{
		this(spot, null);
	}

	public Move(IntPiece newSpot, IntPiece oldSpot)
	{
		pieceMoves = new ArrayList<>();
		add(new PieceMove(newSpot, oldSpot));
	}

	public void add(PieceMove move)
	{
		if (move.isEmpty()) return;
		pieceMoves.add(move);
	}

	public Boolean isEmpty()
	{
		return pieceMoves.isEmpty();
	}

	public void move(Board board)
	{
		for (PieceMove piece: pieceMoves)
		{
			piece.move(board);
		}
	}

	public void move(IntBoard board)
	{
		for (PieceMove piece: pieceMoves)
		{
			piece.move(board);
		}
	}

	public Boolean overwritesFriendly(int color)
	{
		for (PieceMove piece: pieceMoves)
		{
			if (piece.overwritesFriendly(color))
			{
				return true;
			}
		}
		return false;;
	}

	public void remove(int index)
	{
		pieceMoves.remove(index);
	}

	public void remove(PieceMove move)
	{
		pieceMoves.remove(move);
	}
}
