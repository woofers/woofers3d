package com.jaxson.chess.board.move;

import com.jaxson.chess.board.IntBoard;
import com.jaxson.chess.board.IntPiece;
import com.jaxson.chess.ui.Board;
import com.jaxson.chess.ui.Piece;
import com.jaxson.chess.ui.Spot;
import com.jaxson.lib.geom.Point;

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

	public Boolean isPromotion()
	{
		return false;
	}

	private Boolean hasMovedTwoUp()
	{
		return Math.abs(this.oldSpot.location.y - this.newSpot.location.y) == 2;
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
				if (hasMovedTwoUp()) piece.passingIndex = board.getTurn();
			}
		}
		newSpot.setPiece(piece);
		oldSpot.removePiece();
	}

	public void move(IntBoard board)
	{


		board.setSpot(oldSpot, newSpot.location);
		board.setSpot(new IntPiece(), oldSpot.location);

		System.out.println(oldSpot.location.toString());
		System.out.println(newSpot.location.toString());

		newSpot.turn ++;
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
		board.setSpot(oldSpot, oldSpot.location);
		board.setSpot(new IntPiece(), newSpot.location);
		newSpot.turn --;
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
