package com.jaxson.board.move;

import java.util.ArrayList;

import com.jaxson.board.IntBoard;
import com.jaxson.board.IntPiece;
import com.jaxson.ui.board.Board;

public class Move
{
	private IntPiece origin;
	private ArrayList<PieceMove> pieceMoves;

	public Move(IntPiece spot)
	{
		this(spot, null);
	}

	public Move(IntPiece newSpot, IntPiece oldSpot)
	{
		origin = newSpot;
		pieceMoves = new ArrayList<>();
		if (newSpot != null) add(new PieceMove(newSpot, oldSpot));
	}

	public void add(PieceMove move)
	{
		if (move.isEmpty()) return;
		pieceMoves.add(move);
	}

	public IntPiece getOrigin()
	{
		return origin;
	}

	public Boolean isEmpty()
	{
		for (PieceMove piece: pieceMoves)
		{
			if (!piece.isEmpty())
			{
				return false;
			}
		}
		return true;
	}

	public void move(Board board)
	{
		System.out.println("----------");
		for (PieceMove move: pieceMoves)
		{
			System.out.println(move.toString());
			move.move(board);
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
		return false;
	}

	public void remove(int index)
	{
		pieceMoves.remove(index);
	}

	public void remove(PieceMove move)
	{
		pieceMoves.remove(move);
	}

	public void setOrigin(IntPiece value)
	{
		origin = value;
	}

	@Override
	public String toString()
	{
		String string = "";
		string += "----Move----";
		string += "\n";
		for (PieceMove piece: pieceMoves)
		{
			string += piece.toString();
			string += "\n";
		}
		string += "-----------";
		return string;
	}
}
