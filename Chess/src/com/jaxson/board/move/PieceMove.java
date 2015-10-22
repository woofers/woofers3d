package com.jaxson.board.move;

import com.jaxson.board.IntPiece;
import com.jaxson.board.IntBoard;
import com.jaxson.ui.board.Board;
import com.jaxson.ui.board.Piece;
import com.jaxson.ui.board.Spot;

public class PieceMove
{
	private IntPiece newSpot;
	private IntPiece oldSpot;

	public PieceMove(IntPiece spot)
	{
		this(spot, null);
	}

	public PieceMove(IntPiece newSpot, IntPiece oldSpot)
	{
		this.newSpot = newSpot;
		this.oldSpot = oldSpot;
	}

	public Boolean isEmpty()
	{
		return oldSpot == null && newSpot == null;
	}

	public void move(Board board)
	{
		if (oldSpot == null)
		{
			board.getSpot(this.newSpot.location).removePiece();
			return;
		}

		Spot oldSpot, newSpot;
		Piece newPiece;
		oldSpot = board.getSpot(this.oldSpot.location);
		newSpot = board.getSpot(this.newSpot.location);
		newPiece = newSpot.getPiece();
		newSpot.setPiece(oldSpot.getPiece());
		if (newPiece != null) oldSpot.setPiece(newPiece);
	}

	public void move(IntBoard board)
	{
		if (isEmpty()) return;

		if (oldSpot == null)
		{
			board.setSpot(new IntPiece(newSpot.location));
			return;
		}

		board.setSpot(oldSpot, newSpot.location);
		board.setSpot(newSpot, oldSpot.location);
	}

	public Boolean overwritesFriendly(int color)
	{
		if (newSpot == null) return false;
		return color == newSpot.color;
	}

	@Override
	public String toString()
	{
		if (oldSpot == null) return "Removed: " + oldSpot.location.toString();
		return "Moved: " + oldSpot.location.toString() + " to " + newSpot.location.toString();
	}
}
