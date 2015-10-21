package com.jaxson.board;

import java.util.ArrayList;

import com.jaxson.geom.Point;
import com.jaxson.ui.board.Board;
import com.jaxson.ui.board.Piece;

public class IntBoard
{
	private int width, height, turn;
	private int color;
	private IntPiece[][] spots;

	public IntBoard(Board board)
	{
		this(board.gridWidth, board.gridHeight, board.turn);
	}

	public IntBoard(int width, int height, int turn)
	{
		this(width, height, turn, 0);
	}

	public IntBoard(int width, int height, int turn, int color)
	{
		this.width = width;
		this.height = height;
		this.turn = turn;
		this.color = color;
	}

	public IntBoard clone()
	{
		IntBoard newBoard = new IntBoard(width, height, color);
		IntPiece[][] newSpots = new IntPiece[width][height];
		for (int y = 0; y < height; y ++)
		{
			for (int x = 0; x < width; x ++)
			{
				newSpots[x][y] = spots[x][y].clone();
			}
		}
		newBoard.setSpots(newSpots);
		return newBoard;
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

	private ArrayList<IntPiece> getAllAbove(IntPiece piece)
	{
		return getAllByIncrement(piece, 0, -1);
	}

	private ArrayList<IntPiece> getAllBelow(IntPiece piece)
	{
		return getAllByIncrement(piece, 0, +1);
	}

	private ArrayList<IntPiece> getAllByIncrement(IntPiece piece, int xIncrement, int yIncrement)
	{
		ArrayList<IntPiece> spots = new ArrayList<>();
		IntPiece spot = getSpot(piece.location.x + xIncrement, piece.location.y + yIncrement);
		while (spot != null)
		{
			if (!spot.isFriendly(piece.color))
			{
				spots.add(spot);
				if (spot.isEmpty())
				{
					spot = getSpot(spot.location.x + xIncrement, spot.location.y + yIncrement);
					continue;
				}
			}
			break;
		}
		return spots;
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

	private ArrayList<IntPiece> getAllDiagonalBottomLeft(IntPiece piece)
	{
		return getAllByIncrement(piece, -1, +1);
	}

	private ArrayList<IntPiece> getAllDiagonalBottomRight(IntPiece piece)
	{
		return getAllByIncrement(piece, +1, +1);
	}

	private ArrayList<IntPiece> getAllDiagonalTopLeft(IntPiece piece)
	{
		return getAllByIncrement(piece, -1, -1);
	}

	private ArrayList<IntPiece> getAllDiagonalTopRight(IntPiece piece)
	{
		return getAllByIncrement(piece, +1, -1);
	}

	private ArrayList<IntPiece> getAllLeft(IntPiece piece)
	{
		return getAllByIncrement(piece, -1, 0);
	}

	private ArrayList<IntPiece> getAllRight(IntPiece piece)
	{
		return getAllByIncrement(piece, +1, 0);
	}

	private ArrayList<IntPiece> getCastling(IntPiece piece)
	{
		ArrayList<IntPiece> spots = new ArrayList<>();
		if (piece.hasMoved())
		{
			return spots;
		}
		spots.add(getCastlingSpot(piece, +1));
		spots.add(getCastlingSpot(piece, -1));
		return spots;
	}

	private IntPiece getCastlingSpot(IntPiece piece, int direction)
	{
		IntPiece spot = piece;
		while (true)
		{
			spot = getSpot(spot.location.x + direction, spot.location.y);
			if (spot == null)
			{
				return null;
			}
			if (!spot.isEmpty())
			{
				break;
			}
		}
		if (spot.type == Piece.ROOK)
		{
			if (!spot.hasMoved())
			{
				return spot;
			}
		}
		return null;
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

	public ArrayList<IntPiece> getLegalMoves(IntPiece piece)
	{
		ArrayList<IntPiece> moves = new ArrayList<>();
		switch (piece.type)
		{
			case Piece.KING:
				moves.addAll(getSurrondingSpots(piece));
				moves.addAll(getCastling(piece));
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

	private Point getLocation(int x, int y)
	{
		IntPiece piece = getSpot(x, y);
		if (piece != null)
		{
			return piece.location;
		}
		return null;
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
					if (!piece.hasMoved())
					{
						continue;
					}
				}
			}
			break;
		}
		spots.addAll(getPawnCapture(piece));
		spots.addAll(getPawnPassing(piece));
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
			if (spot != null)
			{
				if (!spot.isEnemey(piece.color))
				{
					spots.remove(index);
					continue;
				}
			}
			index ++;
		}
		return spots;
	}

	private ArrayList<IntPiece> getPawnPassing(IntPiece piece)
	{
		ArrayList<IntPiece> spots = new ArrayList<>();
		if (piece.canPass(turn))
		{
			IntPiece spot;
			spot = getSpot(piece.location.x - 1, piece.location.y);
			if (spot != null)
			{
				if (spot.isEnemey(piece.color))
				{
					spots.add(getSpot(piece.location.x - 1, piece.location.y + piece.direction));
				}
			}
			spot = getSpot(piece.location.x + 1, piece.location.y);
			if (spot != null)
			{
				if (spot.isEnemey(piece.color))
				{
					spots.add(getSpot(piece.location.x + 1, piece.location.y + piece.direction));
				}
			}
		}
		return spots;
	}

	private ArrayList<IntPiece> getPieces(int color)
	{
		ArrayList<IntPiece> pieces = new ArrayList<>();
		for (int y = 0; y < height; y ++)
		{
			for (int x = 0; x < width; x ++)
			{
				if (spots[x][y].color == color)
				{
					pieces.add(spots[x][y]);
				}
			}
		}
		return pieces;
	}

	private IntPiece getSpot(int x, int y)
	{
		if (spotExist(x, y))
		{
			return spots[x][y];
		}
		return null;
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

	public void print()
	{
		System.out.println(toString());
	}

	public void setSpots(IntPiece[][] value)
	{
		spots = value;
	}

	private Boolean spotExist(int x, int y)
	{
		if (x >= 0 && x < width)
		{
			if (y >= 0 && y < height)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString()
	{
		String string = "";
		for (int y = 0; y < height; y ++)
		{
			for (int x = 0; x < width; x ++)
			{
				string += spots[x][y].toString();
				string += ", ";
			}
			string += "\n";
		}
		return string;
	}
}
