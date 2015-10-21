package com.jaxson.board;

import java.util.ArrayList;

import com.jaxson.board.move.Move;
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

	private ArrayList<Move> filterFriendly(ArrayList<Move> moves, IntPiece piece)
	{
		Move move;
		int index = 0;
		while (index < moves.size())
		{
			move = moves.get(index);
			if (move.overwritesFriendly(piece.color))
			{
				moves.remove(index);
				continue;
			}
			index ++;
		}
		return moves;
	}

	private ArrayList<Move> filterNull(ArrayList<Move> spots)
	{
		if (spots.remove(null))
		{
			return filterNull(spots);
		}
		return spots;
	}

	private ArrayList<Move> getAllAbove(IntPiece piece)
	{
		return getAllByIncrement(piece, 0, -1);
	}

	private ArrayList<Move> getAllBelow(IntPiece piece)
	{
		return getAllByIncrement(piece, 0, +1);
	}

	private ArrayList<Move> getAllByIncrement(IntPiece piece, int xIncrement, int yIncrement)
	{
		ArrayList<Move> moves = new ArrayList<>();
		IntPiece spot = getSpot(piece.location.x + xIncrement, piece.location.y + yIncrement);
		while (spot != null)
		{
			if (!spot.isFriendly(piece.color))
			{
				moves.add(new Move(spot, piece));
				if (spot.isEmpty())
				{
					spot = getSpot(spot.location.x + xIncrement, spot.location.y + yIncrement);
					continue;
				}
			}
			break;
		}
		return moves;
	}

	private ArrayList<Move> getAllDiagonal(IntPiece piece)
	{
		ArrayList<Move> moves = new ArrayList<>();
		moves.addAll(getAllDiagonalTopLeft(piece));
		moves.addAll(getAllDiagonalTopRight(piece));
		moves.addAll(getAllDiagonalBottomLeft(piece));
		moves.addAll(getAllDiagonalBottomRight(piece));
		return moves;
	}

	private ArrayList<Move> getAllDiagonalBottomLeft(IntPiece piece)
	{
		return getAllByIncrement(piece, -1, +1);
	}

	private ArrayList<Move> getAllDiagonalBottomRight(IntPiece piece)
	{
		return getAllByIncrement(piece, +1, +1);
	}

	private ArrayList<Move> getAllDiagonalTopLeft(IntPiece piece)
	{
		return getAllByIncrement(piece, -1, -1);
	}

	private ArrayList<Move> getAllDiagonalTopRight(IntPiece piece)
	{
		return getAllByIncrement(piece, +1, -1);
	}

	private ArrayList<Move> getAllLeft(IntPiece piece)
	{
		return getAllByIncrement(piece, -1, 0);
	}

	private ArrayList<Move> getAllRight(IntPiece piece)
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

	private ArrayList<Move> getKnight(IntPiece piece)
	{
		ArrayList<Move> moves = new ArrayList<>();
		moves.add(new Move(getSpot(piece.location.x - 1, piece.location.y - 2), piece));
		moves.add(new Move(getSpot(piece.location.x + 1, piece.location.y - 2), piece));
		moves.add(new Move(getSpot(piece.location.x - 1, piece.location.y + 2), piece));
		moves.add(new Move(getSpot(piece.location.x + 1, piece.location.y + 2), piece));
		moves.add(new Move(getSpot(piece.location.x - 2, piece.location.y - 1), piece));
		moves.add(new Move(getSpot(piece.location.x - 2, piece.location.y + 1), piece));
		moves.add(new Move(getSpot(piece.location.x + 2, piece.location.y - 1), piece));
		moves.add(new Move(getSpot(piece.location.x + 2, piece.location.y + 1), piece));
		return filterFriendly(moves, piece);
	}

	public ArrayList<Move> getLegalMoves(IntPiece piece)
	{
		ArrayList<Move> moves = new ArrayList<>();
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
				moves = setOverwrite(moves, piece);
				break;
			case Piece.ROOK:
				moves.addAll(getAllAbove(piece));
				moves.addAll(getAllBelow(piece));
				moves.addAll(getAllLeft(piece));
				moves.addAll(getAllRight(piece));
				moves = setOverwrite(moves, piece);
				break;
			case Piece.BISHOP:
				moves = getAllDiagonal(piece);
				moves = setOverwrite(moves, piece);
				break;
			case Piece.KNIGHT:
				moves = getKnight(piece);
				moves = setOverwrite(moves, piece);
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

	private ArrayList<Move> getPawn(IntPiece piece)
	{
		ArrayList<Move> moves = new ArrayList<>();
		IntPiece spot = piece;
		for (int i = 0; i < 2; i ++)
		{
			spot = getSpot(spot.location.x, spot.location.y + piece.direction);
			if (spot != null)
			{
				if (spot.isEmpty())
				{
					moves.add(new Move(spot, piece));
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

	private ArrayList<Move> getPawnCapture(IntPiece piece)
	{
		ArrayList<Move> spots = new ArrayList<>();
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

	public IntPiece getSpot(Point location)
	{
		return getSpot(location.x, location.y);
	}

	public IntPiece getSpot(int x, int y)
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

	private ArrayList<Move> setOverwrite(ArrayList<Move> moves, IntPiece piece)
	{
		for (Move move: moves)
		{
			move.add(new PieceMove(piece));
		}
		return moves;
	}

	public void setSpots(IntPiece[][] value)
	{
		spots = value;
	}

	public void setSpot(IntPiece value)
	{
		setSpot(value, value.location);
	}

	public void setSpot(IntPiece value, Point location)
	{
		setSpot(value, location.x, location.y);
	}

	public void setSpot(IntPiece value, int x, int y)
	{
		spots[x][y] = value;
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
