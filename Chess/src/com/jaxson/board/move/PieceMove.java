package com.jaxson.board.move;

import com.jaxson.board.IntPiece;
import com.jaxson.board.IntBoard;
import com.jaxson.geom.Point;
import com.jaxson.ui.board.Board;
import com.jaxson.ui.board.Piece;
import com.jaxson.ui.board.Spot;

public class PieceMove
{
	private IntPiece newSpot;
	private IntPiece oldSpot;
	private Piece removedPiece;

	public PieceMove(IntPiece newSpot, IntPiece oldSpot)
	{
		this.newSpot = newSpot;
		this.oldSpot = oldSpot;
	}

	public Boolean isEmpty()
	{
		return newSpot == null;
	}

	public void move(Board board)
	{
		Spot oldSpot, newSpot;
		oldSpot = board.getSpot(this.oldSpot.location);
		newSpot = board.getSpot(this.newSpot.location);
		removedPiece = newSpot.getPiece();
		newSpot.setPiece(oldSpot.getPiece());
		oldSpot.removePiece();
	}

	public void move(IntBoard board)
	{
		board.setSpot(oldSpot, newSpot.location);
		board.setSpot(newSpot, oldSpot.location);
	}

	public Boolean overwritesFriendly()
	{
		return newSpot.color == oldSpot.color;
	}

	@Override
	public String toString()
	{
		return "Moved: " + oldSpot.location.toString() + " to " + newSpot.location.toString();
	}
}
