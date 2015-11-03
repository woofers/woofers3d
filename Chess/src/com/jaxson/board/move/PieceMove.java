package com.jaxson.board.move;

import com.jaxson.board.IntPiece;
import com.jaxson.board.IntBoard;
import com.jaxson.geom.Point;
import com.jaxson.ui.board.Board;
import com.jaxson.ui.board.Piece;
import com.jaxson.ui.board.Spot;

public class PieceMove implements MoveType
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
		Piece piece;
		oldSpot = board.getSpot(this.oldSpot.location);
		newSpot = board.getSpot(this.newSpot.location);

		removedPiece = newSpot.getPiece();
		piece = oldSpot.getPiece();
		if (piece != null)
		{
			piece.passingIndex = -1;
			piece.turn ++;
			if (piece.type == Piece.PAWN)
			{
				if (Math.abs(this.oldSpot.location.y - this.newSpot.location.y) == 2)
				{
					piece.passingIndex = board.turn;
				}
			}
		}
		newSpot.setPiece(piece);
		oldSpot.removePiece();
	}

	public void move(IntBoard board)
	{
		Point oldLocation = oldSpot.location;
		board.setSpot(oldSpot, newSpot.location);
		board.setSpot(newSpot, oldLocation);
	}

	public void undo(Board board)
	{
		Spot oldSpot, newSpot;
		Piece newPiece;
		oldSpot = board.getSpot(this.oldSpot.location);
		newSpot = board.getSpot(this.newSpot.location);

		newPiece = newSpot.getPiece();
		if (newPiece != null)
		{
			newPiece.turn --;
		}

		oldSpot.setPiece(newPiece);
		newSpot.setPiece(removedPiece);
	}

	public void undo(IntBoard board)
	{

	}

	public Boolean overwritesFriendly()
	{
		if (oldSpot == null) return false;
		return newSpot.color == oldSpot.color;
	}

	@Override
	public String toString()
	{
		return "Move: " + oldSpot.location.toString() + " to " + newSpot.location.toString();
	}
}
