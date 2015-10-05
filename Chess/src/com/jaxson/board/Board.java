package com.jaxson.board;

import com.jaxson.ui.Panel;
import java.awt.Color;
import java.awt.GridLayout;

public class Board extends Panel
{
	private static final int size = 8;

	private int gridWidth, gridHeight;
	private Spot[][] board;
	private Color[] colors;

	public Board()
	{
		super();

		colors = new Color[2];
		colors[0] = new Color(209, 139, 71);
		colors[1] = new Color(255, 206, 158);
		gridWidth = 0;
		gridHeight = 0;
	}

	public void createGrid(int width, int height)
	{
		gridWidth = width;
		gridHeight = height;

		setLayout(new GridLayout(gridWidth, gridHeight));
		board = new Spot[gridWidth][gridHeight];
		for (int y = 0; y < gridHeight; y ++)
		{
			for (int x = 0; x < gridWidth; x ++)
			{
				board[x][y] = new Spot(getColor(x, y));
				add(board[x][y]);
			}
		}
		addPieces();
	}

	private void addPieces()
	{
		board[0][gridHeight - size].createPiece(Piece.ROOK, Piece.BLACK);
		board[1][gridHeight - size].createPiece(Piece.KNIGHT, Piece.BLACK);
		board[2][gridHeight - size].createPiece(Piece.BISHOP, Piece.BLACK);
		board[3][gridHeight - size].createPiece(Piece.QUEEN, Piece.BLACK);
		board[4][gridHeight - size].createPiece(Piece.KING, Piece.BLACK);
		board[5][gridHeight - size].createPiece(Piece.BISHOP, Piece.BLACK);
		board[6][gridHeight - size].createPiece(Piece.KNIGHT, Piece.BLACK);
		board[7][gridHeight - size].createPiece(Piece.ROOK, Piece.BLACK);
		for (int x = 0; x < gridWidth; x ++)
		{
			board[x][gridHeight - size + 1].createPiece(Piece.PAWN, Piece.BLACK);
		}

		int start = gridWidth - size;
		for (int x = start; x < gridWidth - (start); x ++)
		{
			board[x][gridHeight - 2].createPiece(Piece.PAWN, Piece.WHITE);
		}


		board[0][gridHeight - 1].createPiece(Piece.ROOK, Piece.WHITE);
		board[1][gridHeight - 1].createPiece(Piece.KNIGHT, Piece.WHITE);
		board[2][gridHeight - 1].createPiece(Piece.BISHOP, Piece.WHITE);
		board[3][gridHeight - 1].createPiece(Piece.QUEEN, Piece.WHITE);
		board[4][gridHeight - 1].createPiece(Piece.KING, Piece.WHITE);
		board[5][gridHeight - 1].createPiece(Piece.BISHOP, Piece.WHITE);
		board[6][gridHeight - 1].createPiece(Piece.KNIGHT, Piece.WHITE);
		board[7][gridHeight - 1].createPiece(Piece.ROOK, Piece.WHITE);
	}

	public void removeGrid()
	{
		for (int x = 0; x < gridWidth; x ++)
		{
			for (int y = 0; y < gridHeight; y ++)
			{
				remove(board[x][y]);
				board[x][y] = null;
			}
		}
		draw();
	}

	private Color getColor(int x, int y)
	{
		if (isEven(y))
		{
			if (isEven(x))
			{
				return colors[1];
			}
			return colors[0];
		}
		if (isEven(x))
		{
			return colors[0];
		}
		return colors[1];
	}

	private boolean isEven(int i)
	{
		return i % 2 == 0;
	}
}
