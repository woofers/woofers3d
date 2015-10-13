package com.jaxson.board;

import com.jaxson.board.Board;
import com.jaxson.board.containers.*;
import java.util.ArrayList;

public class IntBoard
{
	private int gridWidth, gridHeight;
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

	public ArrayList<Point> getLegalMoves(IntPiece piece)
	{
		ArrayList<Point> moves = new ArrayList<>();

		switch (piece.type)
		{
			case Piece.KING:
				moves = getSurrondingSpots(piece);
				break;
			case Piece.QUEEN:
				moves.addAll(getAllAbove(piece));
				moves.addAll(getAllBelow(piece));
				moves.addAll(getAllLeft(piece));
				moves.addAll(getAllRight(piece));
				moves.addAll(getAllDiagonal(piece));
				break;
			case Piece.ROOK:
				moves.addAll(getAllAbove(piece));
				moves.addAll(getAllBelow(piece));
				moves.addAll(getAllLeft(piece));
				moves.addAll(getAllRight(piece));
				break;
			case Piece.BISHOP:
				moves = getAllDiagonal(piece);
				break;
			case Piece.KNIGHT:
				moves = getKnight(piece);
				break;
			case Piece.PAWN:
				moves = getPawn(piece);
				break;
		}
		return moves;
	}

	private ArrayList<Point> getAllAbove(IntPiece piece)
	{
		return getAllByIncrement(piece, 0, -1);
	}


	private ArrayList<Point> getAllBelow(IntPiece piece)
	{
		return getAllByIncrement(piece, 0, +1);
	}

	private ArrayList<Point> getAllLeft(IntPiece piece)
	{
		return getAllByIncrement(piece, -1, 0);
	}

	private ArrayList<Point> getAllRight(IntPiece piece)
	{
		return getAllByIncrement(piece, +1, 0);
	}

	private ArrayList<Point> getAllDiagonalTopLeft(IntPiece piece)
	{
		return getAllByIncrement(piece, -1, -1);
	}

	private ArrayList<Point> getAllDiagonalTopRight(IntPiece piece)
	{
		return getAllByIncrement(piece, +1, -1);
	}

	private ArrayList<Point> getAllDiagonalBottomLeft(IntPiece piece)
	{
		return getAllByIncrement(piece, -1, +1);
	}

	private ArrayList<Point> getAllDiagonalBottomRight(IntPiece piece)
	{
		return getAllByIncrement(piece, +1, +1);
	}

	private ArrayList<Point> getAllDiagonal(IntPiece piece)
	{
		ArrayList<Point> spots = new ArrayList<>();
		spots.addAll(getAllDiagonalTopLeft(piece));
		spots.addAll(getAllDiagonalTopRight(piece));
		spots.addAll(getAllDiagonalBottomLeft(piece));
		spots.addAll(getAllDiagonalBottomRight(piece));
		return spots;
	}

	private ArrayList<Point> getSurrondingSpots(IntPiece piece)
	{
		ArrayList<Point> spots = new ArrayList<>();
		spots.add(getLocation(piece.location.x - 1, piece.location.y - 1));
		spots.add(getLocation(piece.location.x, piece.location.y - 1));
		spots.add(getLocation(piece.location.x + 1, piece.location.y - 1));
		spots.add(getLocation(piece.location.x - 1, piece.location.y));
		spots.add(getLocation(piece.location.x + 1, piece.location.y));
		spots.add(getLocation(piece.location.x - 1, piece.location.y + 1));
		spots.add(getLocation(piece.location.x, piece.location.y + 1));
		spots.add(getLocation(piece.location.x + 1, piece.location.y + 1));
		spots = filterMoves(spots, piece);
		return spots;
	}

	private ArrayList<Point> getKnight(IntPiece piece)
	{
		ArrayList<Point> spots = new ArrayList<>();
		spots.add(getLocation(piece.location.x - 1, piece.location.y - 2));
		spots.add(getLocation(piece.location.x + 1, piece.location.y - 2));
		spots.add(getLocation(piece.location.x - 1, piece.location.y + 2));
		spots.add(getLocation(piece.location.x + 1, piece.location.y + 2));
		spots.add(getLocation(piece.location.x - 2, piece.location.y - 1));
		spots.add(getLocation(piece.location.x - 2, piece.location.y + 1));
		spots.add(getLocation(piece.location.x + 2, piece.location.y - 1));
		spots.add(getLocation(piece.location.x + 2, piece.location.y + 1));
		spots = filterMoves(spots, piece);
		return spots;
	}

	private ArrayList<Point> getPawn(IntPiece piece)
	{
		ArrayList<Point> spots = new ArrayList<>();
		Point spot;
		int increment;
		if (piece.color == Piece.BLACK)
		{
			increment = +1;
		}
		else
		{
			increment = -1;
		}
		spot = getLocation(piece.location.x, piece.location.y + increment);
		if (this.spots[spot.x][spot.y].type != 0)
		{
			return spots;
		}
		spots.add(spot);
		if (!piece.hasMoved)
		{
			spot = getLocation(piece.location.x, piece.location.y + increment + increment);
			if (this.spots[spot.x][spot.y].type == 0)
			{
				spots.add(spot);
			}
		}
		return spots;
	}

	private ArrayList<Point> filterMoves(ArrayList<Point> spots, IntPiece piece)
	{
		Point spot;
		int index = 0;
		while (index < spots.size())
		{
			spot = spots.get(index);
			if (spot != null)
			{
				if (getSpot(spot.x, spot.y).color == piece.color)
				{
					spots.remove(index);
					continue;
				}
			}
			index ++;
		}
		return spots;
	}

	private ArrayList<Point> getAllByIncrement(IntPiece piece, int xIncrement, int yIncrement)
	{
		ArrayList<Point> spots = new ArrayList<>();
		IntPiece spot = getSpot(piece.location.x + xIncrement, piece.location.y + yIncrement);
		if (spot == null)
		{
			return spots;
		}
		while (spot.type == 0)
		{
			spots.add(spot.location);
			spot = getSpot(spot.location.x + xIncrement, spot.location.y + yIncrement);
			if (spot == null)
			{
				break;
			}
		}
		if (spot != null)
		{
			if (spot.color != piece.color)
			{
				spots.add(spot.location);
			}
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
