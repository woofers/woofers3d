package com.jaxson.board.move;

import com.jaxson.board.IntBoard;
import com.jaxson.board.IntPiece;
import com.jaxson.ui.board.Board;
import com.jaxson.util.MyArrayList;

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

	public Boolean isEmpty()
	{
		return moveTypes.isEmpty();
	}

	public void move(Board board)
	{
		board.turn ++;
		for (MoveType move: moveTypes)
		{
			move.move(board);
		}
		board.draw();
	}

	public void move(IntBoard board)
	{
		for (MoveType move: moveTypes)
		{
			move.move(board);
		}
		board.swapColors();
	}

	public void remove(int index)
	{
		moveTypes.remove(index);
	}

	public void remove(PieceMove move)
	{
		moveTypes.remove(move);
	}

	public void setOrigin(IntPiece value)
	{
		origin = value;
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
		board.turn --;
		for (MoveType move: moveTypes)
		{
			move.undo(board);
		}
		board.draw();
	}

	public void undo(IntBoard board)
	{
		for (MoveType move: moveTypes)
		{
			move.undo(board);
		}
		board.swapColors();
	}
}
