package com.jaxson.board;

import com.jaxson.board.Board;
import com.jaxson.board.containers.*;
import java.util.ArrayList;

public class IntBoard
{
	private int gridWidth, gridHeight = 0;
	private int currentColor;
	private Board board;
	private IntPiece[][] spots;

	public IntBoard(Board board)
	{
		this.board = board;
		gridWidth = board.gridWidth;
		gridHeight = board.gridHeight;
	}

	public void setBoard(IntPiece[][] spots)
	{
		this.spots = spots;
	}

	public void displayMoves(IntPiece piece)
	{
		board.displayMoves(getLegalMoves(piece));
	}

	private ArrayList<IntPiece> getPieces(int color)
	{
		ArrayList<IntPiece> pieces = new ArrayList<>();
		for (int y = 0; y < gridHeight; y ++)
		{
			for (int x = 0; x < gridWidth; x ++)
			{
				if (spots[x][y].color == color)
				{
					pieces.add(spots[x][y]);
				}
			}
		}
		return pieces;
	}

	private ArrayList<Point> getLegalMoves(IntPiece piece)
	{
		ArrayList<Point> moves = new ArrayList<>();

		switch (piece.type)
		{
			case Piece.KING:
				moves = getSurrondingSpots(piece.location);
				break;
			case Piece.QUEEN:
				moves = getAllAbove(piece.location);
				break;
			case Piece.ROOK:
				break;
			case Piece.BISHOP:
				break;
			case Piece.KNIGHT:
				break;
			case Piece.PAWN:
				break;
			default:
				break;
		}
		return moves;
	}

	private ArrayList<Point> getSurrondingSpots(Point point)
	{
		ArrayList<Point> spots = new ArrayList<>();
		spots.add(getLocation(point.x - 1, point.y - 1));
		spots.add(getLocation(point.x, point.y - 1));
		spots.add(getLocation(point.x + 1, point.y - 1));
		spots.add(getLocation(point.x - 1, point.y));
		spots.add(getLocation(point.x + 1, point.y));
		spots.add(getLocation(point.x - 1, point.y + 1));
		spots.add(getLocation(point.x, point.y + 1));
		spots.add(getLocation(point.x + 1, point.y + 1));
		return spots;
	}

	private ArrayList<Point> getAllAbove(Point point)
	{
		ArrayList<Point> spots = new ArrayList<>();
		IntPiece spot = getSpot(point.x - 1, point.y);
		while (spot.type == 0)
		{
			//spots.add(spot.location);
			//spot = getSpot(spot.location.x - 1, spot.location.y);
		}
		return spots;
	}

	private Point getLocation(int x, int y)
	{
		IntPiece piece = getSpot(x, y);
		if (piece != null)
		{
			return piece.location;
		}
		return null;
	}

	private IntPiece getSpot(int x, int y)
	{
		if (x >= 0 && x < gridWidth)
		{
			if (y >= 0 && y < gridHeight)
			{
				return spots[x][y];
			}
		}
		return null;
	}

	public String toString()
	{
		String string = "";
		for (int y = 0; y < gridHeight; y ++)
		{
			for (int x = 0; x < gridWidth; x ++)
			{
				string = string + ", ";
				string = string + Integer.toString(spots[x][y].toInt());
			}
		}
		return string;
	}

	public void print()
	{
		String string = "";
		for (int y = 0; y < gridHeight; y ++)
		{
			for (int x = 0; x < gridWidth; x ++)
			{
				string = string + Integer.toString(spots[x][y].toInt());
				if (spots[x][y].toInt() == 0)
				{
					string = string + "0";
				}
				string = string + ", ";
			}
			System.out.println(string);
			string = "";
		}
	}
}
