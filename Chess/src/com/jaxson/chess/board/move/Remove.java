package com.jaxson.chess.board.move;

import com.jaxson.chess.board.IntBoard;
import com.jaxson.chess.board.IntPiece;
import com.jaxson.chess.ui.Board;
import com.jaxson.chess.ui.Piece;
import com.jaxson.chess.ui.Spot;
import com.jaxson.lib.geom.Point;

public class Remove implements MoveType
{
	private IntPiece spot;
	private Piece piece;

	public Remove(IntPiece spot)
	{
		this.spot = spot;
	}

	public Boolean isEmpty()
	{
		return spot == null;
	}

	public Boolean isPromotion()
	{
		return false;
	}

	public void move(Board board)
	{
		Spot spot = board.getSpot(this.spot);
		piece = spot.getPiece();
		spot.removePiece();
	}

	public void move(IntBoard board)
	{

	}

	public void undo(Board board)
	{
		Spot spot = board.getSpot(this.spot);
		spot.setPiece(piece);
	}

	public void undo(IntBoard board)
	{

	}

	public Boolean overwritesFriendly()
	{
		return false;
	}

	@Override
	public String toString()
	{
		return "Removed: " + spot.location.toString();
	}
}
