package com.jaxson.board;

import com.jaxson.board.containers.*;

public class IntBoard
{
	private int gridWidth, gridHeight = 0;
	private IntPiece[][] board;

	public IntBoard(int width, int height)
	{
		gridWidth = width;
		gridHeight = height;
	}

	public void setBoard(IntPiece[][] board)
	{
		this.board = board;
	}

	private void getPieces(int color)
	{
		IntPiece[] pieces = new IntPiece[2];
		for (IntPiece[] rows: board)
		{
			for (IntPiece piece: rows)
			{
				if (piece.color == color)
				{

				}
			}
		}
	}

	private Point[] getLegalMoves(IntPiece piece, Point spot)
	{
		Point[] moves = new Point[7];

		switch (piece.type)
		{
			case Piece.KING:
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
		return moves;
	}


	private Point[] getSurrondingSpots(Point point)
	{
		Point[] spots = new Point[8];
		return spots;
	}

	public String toString()
	{
		String string = "";
		for (int y = 0; y < gridHeight; y ++)
		{
			for (int x = 0; x < gridWidth; x ++)
			{
				string = string + ", ";
				string = string + Integer.toString(board[x][y].toInt());
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
				string = string + Integer.toString(board[x][y].toInt());
				if (board[x][y].toInt() == 0)
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
