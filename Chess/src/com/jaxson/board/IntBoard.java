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

	public ArrayList<IntPiece> getLegalMoves(IntPiece piece)
	{
		ArrayList<IntPiece> moves = new ArrayList<>();

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
		return filterNull(moves);
	}

	private ArrayList<IntPiece> getAllAbove(IntPiece piece)
	{
		return getAllByIncrement(piece, 0, -1);
	}

	private ArrayList<IntPiece> getAllBelow(IntPiece piece)
	{
		return getAllByIncrement(piece, 0, +1);
	}

	private ArrayList<IntPiece> getAllLeft(IntPiece piece)
	{
		return getAllByIncrement(piece, -1, 0);
	}

	private ArrayList<IntPiece> getAllRight(IntPiece piece)
	{
		return getAllByIncrement(piece, +1, 0);
	}

	private ArrayList<IntPiece> getAllDiagonalTopLeft(IntPiece piece)
	{
		return getAllByIncrement(piece, -1, -1);
	}

	private ArrayList<IntPiece> getAllDiagonalTopRight(IntPiece piece)
	{
		return getAllByIncrement(piece, +1, -1);
	}

	private ArrayList<IntPiece> getAllDiagonalBottomLeft(IntPiece piece)
	{
		return getAllByIncrement(piece, -1, +1);
	}

	private ArrayList<IntPiece> getAllDiagonalBottomRight(IntPiece piece)
	{
		return getAllByIncrement(piece, +1, +1);
	}

	private ArrayList<IntPiece> getAllDiagonal(IntPiece piece)
	{
		ArrayList<IntPiece> spots = new ArrayList<>();
		spots.addAll(getAllDiagonalTopLeft(piece));
		spots.addAll(getAllDiagonalTopRight(piece));
		spots.addAll(getAllDiagonalBottomLeft(piece));
		spots.addAll(getAllDiagonalBottomRight(piece));
		return spots;
	}

	private ArrayList<IntPiece> getSurrondingSpots(IntPiece piece)
	{
		ArrayList<IntPiece> spots = new ArrayList<>();
		spots.add(getSpot(piece.location.x - 1, piece.location.y - 1));
		spots.add(getSpot(piece.location.x, piece.location.y - 1));
		spots.add(getSpot(piece.location.x + 1, piece.location.y - 1));
		spots.add(getSpot(piece.location.x - 1, piece.location.y));
		spots.add(getSpot(piece.location.x + 1, piece.location.y));
		spots.add(getSpot(piece.location.x - 1, piece.location.y + 1));
		spots.add(getSpot(piece.location.x, piece.location.y + 1));
		spots.add(getSpot(piece.location.x + 1, piece.location.y + 1));;
		return filterFriendly(spots, piece);
	}

	private ArrayList<IntPiece> getKnight(IntPiece piece)
	{
		ArrayList<IntPiece> spots = new ArrayList<>();
		spots.add(getSpot(piece.location.x - 1, piece.location.y - 2));
		spots.add(getSpot(piece.location.x + 1, piece.location.y - 2));
		spots.add(getSpot(piece.location.x - 1, piece.location.y + 2));
		spots.add(getSpot(piece.location.x + 1, piece.location.y + 2));
		spots.add(getSpot(piece.location.x - 2, piece.location.y - 1));
		spots.add(getSpot(piece.location.x - 2, piece.location.y + 1));
		spots.add(getSpot(piece.location.x + 2, piece.location.y - 1));
		spots.add(getSpot(piece.location.x + 2, piece.location.y + 1));
		return filterFriendly(spots, piece);
	}

	private ArrayList<IntPiece> getPawn(IntPiece piece)
	{
		ArrayList<IntPiece> spots = new ArrayList<>();
		IntPiece spot = piece;
		for (int i = 0; i < 2; i ++)
		{
			spot = getSpot(spot.location.x, spot.location.y + piece.direction);
			if (spot != null)
			{
				if (spot.isEmpty())
				{
					spots.add(spot);
					if (piece.hasMoved)
					{
						break;
					}
					continue;
				}
			}
			break;
		}
		spots.addAll(getPawnCapture(piece));
		return spots;
	}

	private ArrayList<IntPiece> getPawnCapture(IntPiece piece)
	{
		ArrayList<IntPiece> spots = new ArrayList<>();
		spots.add(getSpot(piece.location.x + 1, piece.location.y + piece.direction));
		spots.add(getSpot(piece.location.x - 1, piece.location.y + piece.direction));
		IntPiece spot;
		int index = 0;
		while (index < spots.size())
		{
			spot = spots.get(index);
			if (spot.color == piece.color || spot.isEmpty())
			{
					spots.remove(index);
					continue;
			}
			index ++;
		}
		return spots;
	}

	private ArrayList<IntPiece> getAllByIncrement(IntPiece piece, int xIncrement, int yIncrement)
	{
		ArrayList<IntPiece> spots = new ArrayList<>();
		IntPiece spot = piece;
		while (spot != null)
		{
			spot = getSpot(spot.location.x + xIncrement, spot.location.y + yIncrement);
			if (spot != null)
			{
				if (!spot.isFriendly(piece.color))
				{
					spots.add(spot);
					if (!spot.isEmpty())
					{
						break;
					}
					continue;
				}
			}
			return spots;
		}
		return spots;
	}

	private ArrayList<IntPiece> filterFriendly(ArrayList<IntPiece> spots, IntPiece piece)
	{
		IntPiece spot;
		int index = 0;
		while (index < spots.size())
		{
			spot = spots.get(index);
			if (spot != null)
			{
				if (spot.color == piece.color)
				{
					spots.remove(index);
					continue;
				}
			}
			index ++;
		}
		return spots;
	}

	private ArrayList<IntPiece> filterNull(ArrayList<IntPiece> spots)
	{
		if (spots.remove(null))
		{
			return filterNull(spots);
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
		if (spotExist(x, y))
		{
			return spots[x][y];
		}
		return null;
	}

	private Boolean spotExist(int x, int y)
	{
		if (x >= 0 && x < gridWidth)
		{
			if (y >= 0 && y < gridHeight)
			{
				return true;
			}
		}
		return false;
	}

	public String toString()
	{
		String string = "";
		for (int y = 0; y < gridHeight; y ++)
		{
			for (int x = 0; x < gridWidth; x ++)
			{
				string += spots[x][y].toString();
				string += ", ";
			}
			string += "\n";
		}
		return string;
	}

	public void print()
	{
		System.out.println(toString());
	}
}
