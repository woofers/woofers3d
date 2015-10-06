package com.jaxson.board;

import com.jaxson.ui.Panel;
import java.awt.Color;
import java.awt.GridLayout;

public class Board extends Panel
{
	private static final int size = 8;
	private static final Color light = new Color(209, 139, 71);
	private static final Color dark = new Color(255, 206, 158);

	private int gridWidth, gridHeight;
	private Spot[][] board;

	public Board()
	{
		super();
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
				board[x][y] = new Spot(getColor(x, y), this);
				add(board[x][y]);
			}
		}
		Spot leftSpot, rightSpot, topSpot, bottomSpot;
		for (int y = 0; y < gridHeight; y ++)
		{
			for (int x = 0; x < gridWidth; x ++)
			{
				leftSpot = getSpot(x - 1, y);
				rightSpot = getSpot(x + 1, y);
				topSpot = getSpot(x, y - 1);
				bottomSpot = getSpot(x, y + 1);
				board[x][y].setLink(leftSpot, rightSpot, topSpot, bottomSpot);
			}
		}
		addPieces();
	}

	private Spot getSpot(int x, int y)
	{
		if (x >= 0 && x < gridWidth)
		{
			if (y >= 0 && y < gridHeight)
			{
				return board[x][y];
			}
		}
		return null;
	}

	private void addPieces()
	{
		createBottomRow(0, Piece.BLACK);
		createBottomRow(gridHeight - 1, Piece.WHITE);
		createPawns(1, Piece.BLACK);
		createPawns(gridHeight - 2, Piece.WHITE);
	}

	private void createPawns(int row, int color)
	{
		int delta, start;
		delta = gridWidth - size;
		start = zero(size / 2 + delta / 2);
		for (int x = start; x > start - size / 2; x --)
		{
			board[x][row].createPiece(Piece.PAWN, color);
		}
		start ++;
		for (int x = start; x < start + size / 2; x ++)
		{
			board[x][row].createPiece(Piece.PAWN, color);
		}
	}

	private void createBottomRow(int row, int color)
	{
		int delta, queen, king;
		delta = gridWidth - size;
		queen = zero(size / 2 + delta / 2);
		king = queen + 1;
		board[queen - 3][row].createPiece(Piece.ROOK, color);
		board[queen - 2][row].createPiece(Piece.KNIGHT, color);
		board[queen - 1][row].createPiece(Piece.BISHOP, color);
		board[queen][row].createPiece(Piece.QUEEN, color);
		board[king][row].createPiece(Piece.KING, color);
		board[king + 1][row].createPiece(Piece.BISHOP, color);
		board[king + 2][row].createPiece(Piece.KNIGHT, color);
		board[king + 3][row].createPiece(Piece.ROOK, color);
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

	public void deselect()
	{
		for (int y = 0; y < gridHeight; y ++)
		{
			for (int x = 0; x < gridWidth; x ++)
			{
				board[x][y].deselect();
			}
		}
	}

	private Color getColor(int x, int y)
	{
		if (isEven(y))
		{
			if (isEven(x))
			{
				return dark;
			}
			return light;
		}
		if (isEven(x))
		{
			return light;
		}
		return dark;
	}

	private int zero(int i)
	{
		if (i <= 0)
		{
			return 0;
		}
		return i - 1;
	}

	private boolean isEven(int i)
	{
		return i % 2 == 0;
	}
}
