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

	private int getColor(int piece)
	{
		String string = Integer.toString(piece);
		return Integer.parseInt(string.substring(0, 1));
	}

	private int getType(int piece)
	{
		String string = Integer.toString(piece);
		return Integer.parseInt(string.substring(1, 2));
	}

	public void getLegalMoves(int piece)
	{
		Point[] moves = new Point[7];

		switch (piece.type)
		{
			case Piece.KING:
				moves = getSurrondingSpots();
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

	public String toString()
	{
		String string = "";
		for (int y = 0; y < gridHeight; y ++)
		{
			for (int x = 0; x < gridWidth; x ++)
			{
				string = string + ", ";
				string = string + Integer.toString(board[x][y].color) + Integer.toString(board[x][y].type);
			}
		}
	}

	public void print()
	{
		String string = "";
		for (int y = 0; y < gridHeight; y ++)
		{
			for (int x = 0; x < gridWidth; x ++)
			{
				string = string + Integer.toString(board[x][y]);
				if (board[x][y] == 0)
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
