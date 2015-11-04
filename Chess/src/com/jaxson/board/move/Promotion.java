package com.jaxson.board.move;

import com.jaxson.board.IntPiece;
import com.jaxson.board.IntBoard;
import com.jaxson.geom.Point;
import com.jaxson.ui.board.Board;
import com.jaxson.ui.board.Piece;
import com.jaxson.ui.board.Spot;

public class Promotion implements MoveType
{
	private int type;
	private IntPiece spot;
	private Point location;
	private Piece oldPiece;

	public Promotion(IntPiece spot, int type)
	{
		this.spot = spot;
		this.type = type;
		this.location = spot.location.clone();
	}

	public Boolean isEmpty()
	{
		return spot == null;
	}

	public void move(Board board)
	{
		Piece piece = new Piece(type, spot.color);
		Spot spot = board.getSpot(location);
		oldPiece = spot.getPiece();
		spot.setPiece(piece);
	}

	public void move(IntBoard board)
	{
		spot.type = type;
	}

	public void undo(Board board)
	{
		Spot spot = board.getSpot(location);
		spot.setPiece(oldPiece);
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
		return "Promote: " + spot.location.toString() + " to " + type;
	}
}
