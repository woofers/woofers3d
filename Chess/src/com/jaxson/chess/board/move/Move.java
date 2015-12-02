package com.jaxson.chess.board.move;

import com.jaxson.chess.board.IntBoard;
import com.jaxson.chess.board.IntPiece;
import com.jaxson.chess.ui.Board;
import com.jaxson.lib.util.MyArrayList;

public class Move
{
	private IntPiece origin;
	private MyArrayList<MoveType> moveTypes;

	public Move(IntPiece origin)
	{
		this(origin, new PieceMove(origin, null));
	}

	public Move(IntPiece newSpot, IntPiece oldSpot)
	{
		this(newSpot, new PieceMove(newSpot, oldSpot));
	}

	public Move(IntPiece origin, MoveType move)
	{
		this.origin = origin;
		moveTypes = new MyArrayList<>();
		add(move);
	}

	public void add(MoveType move)
	{
		if (move == null) return;
		if (move.isEmpty()) return;
		if (move.overwritesFriendly()) return;
		moveTypes.add(move);
	}

	public IntPiece getOrigin()
	{
		return origin;
	}

	public Boolean hasPromotion()
	{
		for (MoveType move: moveTypes)
		{
			if (move.isPromotion()) return true;
		}
		return false;
	}

	public Boolean isEmpty()
	{
		return moveTypes.isEmpty();
	}

	public void move(Board board)
	{
		board.setTurn(board.getTurn() + 1);
		for (MoveType move: moveTypes)
		{
			move.move(board);
		}
		if (!hasPromotion()) board.swapColors();
		board.draw();
	}

	public void move(IntBoard board)
	{
		for (MoveType move: moveTypes)
		{
			move.move(board);
		}
		if (!hasPromotion()) board.swapColors();
	}

	public void remove(int index)
	{
		moveTypes.remove(index);
	}

	public void remove(PieceMove move)
	{
		moveTypes.remove(move);
	}

	public void setOrigin(IntPiece origin)
	{
		this.origin = origin;
	}

	@Override
	public String toString()
	{
		String string = "\n";
		string += "MOVE: " + origin.location.toString();
		for (MoveType move: moveTypes)
		{
			string += "\n";
			string += move.toString();
		}
		return string;
	}

	public void undo(Board board)
	{
		board.setTurn(board.getTurn() - 1);
		for (MoveType move: moveTypes)
		{
			move.undo(board);
		}
		if (!hasPromotion()) board.swapColors();
		board.draw();
	}

	public void undo(IntBoard board)
	{
		for (MoveType move: moveTypes)
		{
			move.undo(board);
		}
		if (!hasPromotion()) board.swapColors();
	}
}
