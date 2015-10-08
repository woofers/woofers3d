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

	public IntBoard(Board spots)
	{
		this.board = board;
		gridWidth = spots.gridWidth;
		gridHeight = spots.gridHeight;
	}

	public void setBoard(IntPiece[][] board)
	{
		this.spots = board;
	}

	public void displayMoves(IntPiece piece)
	{
		ArrayList<Point> arrrayList = new ArrayList<>();
		arrrayList = getLegalMoves(piece);
		//board.displayMoves(getLegalMoves(piece));
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
		moves.add(new Point(0,0));
		return moves;
	}

	private ArrayList<Point> getSurrondingSpots(Point point)
	{
		ArrayList<Point> spots = new ArrayList<>();
		//ArrayList.add(getSpot(point.x - 1, point.y - 1).location);
		/*
		ArrayList.add(getSpot(point.x, point.y - 1).location);
		ArrayList.add(getSpot(point.x + 1, point.y - 1).location);
		ArrayList.add(getSpot(point.x - 1, point.y).location);
		ArrayList.add(getSpot(point.x + 1, point.y).location);
		ArrayList.add(getSpot(point.x - 1, point.y + 1).location);
		ArrayList.add(getSpot(point.x, point.y + 1).location);
		ArrayList.add(getSpot(point.x + 1, point.y + 1).location);
		*/
		return spots;
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
