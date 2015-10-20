package com.jaxson.ui.board;

import java.awt.GridLayout;

import com.jaxson.ai.EasyPlayer;
import com.jaxson.ai.HardPlayer;
import com.jaxson.board.IntBoard;
import com.jaxson.board.IntPiece;
import com.jaxson.geom.Point;
import com.jaxson.ui.Panel;
import com.jaxson.ui.Window;

public class Board<T extends Window> extends Panel
{
	private static final int SIZE = 8;

	public int gridWidth, gridHeight, turn;
	private T window;
	private Spot[][] spots;

	public Board(T window)
	{
		super();
		this.window = window;
		turn = 0;
	}

	private void addPieces()
	{
		createBottomRow(0, Piece.BLACK);
		createBottomRow(gridHeight - 1, Piece.WHITE);
		createPawns(1, Piece.BLACK);
		createPawns(gridHeight - 2, Piece.WHITE);
	}

	private void createBottomRow(int row, int color)
	{
		int delta, queen, king;
		delta = gridWidth - SIZE;
		queen = zero(SIZE / 2 + delta / 2);
		king = queen + 1;
		spots[queen - 3][row].createPiece(Piece.ROOK, color);
		spots[queen - 2][row].createPiece(Piece.KNIGHT, color);
		spots[queen - 1][row].createPiece(Piece.BISHOP, color);
		spots[queen][row].createPiece(Piece.QUEEN, color);
		spots[king][row].createPiece(Piece.KING, color);
		spots[king + 1][row].createPiece(Piece.BISHOP, color);
		spots[king + 2][row].createPiece(Piece.KNIGHT, color);
		spots[king + 3][row].createPiece(Piece.ROOK, color);
	}

	public void createGrid(int width, int height)
	{
		gridWidth = width;
		gridHeight = height;

		setLayout(new GridLayout(gridWidth, gridHeight));
		spots = new Spot[gridWidth][gridHeight];
		for (int y = 0; y < gridHeight; y ++)
		{
			for (int x = 0; x < gridWidth; x ++)
			{
				spots[x][y] = new Spot(new Point(x, y), this);
				add(spots[x][y]);
				if (spots[x][y].isPossibleEnd())
				{
					spots[x][y].setWindow(window);
				}
			}
		}
		addPieces();
	}

	private void createPawns(int row, int color)
	{
		int delta, start;
		delta = gridWidth - SIZE;
		start = zero(SIZE / 2 + delta / 2);
		for (int x = start; x > start - SIZE / 2; x --)
		{
			spots[x][row].createPiece(Piece.PAWN, color);
		}
		start ++;
		for (int x = start; x < start + SIZE / 2; x ++)
		{
			spots[x][row].createPiece(Piece.PAWN, color);
		}
	}

	public void deselect()
	{
		for (int y = 0; y < gridHeight; y ++)
		{
			for (int x = 0; x < gridWidth; x ++)
			{
				spots[x][y].deselect();
			}
		}
	}

	public Spot getSpot(int x, int y)
	{
		if (spotExist(x, y))
		{
			return spots[x][y];
		}
		return null;
	}

	public void move()
	{

	}

	public void removeGrid()
	{
		for (int y = 0; y < gridHeight; y ++)
		{
			for (int x = 0; x < gridWidth; x ++)
			{
				remove(spots[x][y]);
				spots[x][y] = null;
			}
		}
		draw();
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

	public IntBoard toIntBoard()
	{
		IntBoard intBoard = new IntBoard(this);
		IntPiece[][] spots = new IntPiece[gridWidth][gridHeight];
		for (int y = 0; y < gridHeight; y ++)
		{
			for (int x = 0; x < gridWidth; x ++)
			{
				spots[x][y] = this.spots[x][y].toIntPiece();
			}
		}
		intBoard.setSpots(spots);
		return intBoard;
	}

	private int zero(int i)
	{
		if (i <= 0)
		{
			return 0;
		}
		return i - 1;
	}
}
