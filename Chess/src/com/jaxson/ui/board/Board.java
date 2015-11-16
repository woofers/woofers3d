package com.jaxson.ui.board;

import java.awt.GridLayout;

import com.jaxson.ai.EasyPlayer;
import com.jaxson.ai.HardPlayer;
import com.jaxson.board.IntBoard;
import com.jaxson.board.IntPiece;
import com.jaxson.board.move.MoveHistory;
import com.jaxson.geom.Point;
import com.jaxson.ui.Panel;
import com.jaxson.ui.Window;

public class Board extends Panel
{
	private static final int REGULAR_SIZE = 8;

	public int gridWidth, gridHeight, turn;
	private ChessWindow window;
	private MoveHistory moveHistory;
	private Spot[][] spots;
	private Options options;

	public Board(ChessWindow window)
	{
		super();
		this.window = window;
		moveHistory = new MoveHistory();
		turn = 0;
	}

	private void addPieces()
	{
		int topRow, bottomRow;
		topRow = getTopRow();;
		bottomRow = getBottomRow();
		createBottomRow(topRow, Piece.BLACK);
		createPawns(topRow + 1, Piece.BLACK);
		createBottomRow(bottomRow, Piece.WHITE);
		createPawns(bottomRow - 1, Piece.WHITE);
	}

	private void createBottomRow(int row, int color)
	{
		int delta, queen, king;
		delta = gridWidth - REGULAR_SIZE;
		queen = zero(REGULAR_SIZE / 2 + delta / 2);
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

		setLayout(new GridLayout(gridHeight, gridWidth));
		spots = new Spot[gridWidth][gridHeight];
		for (int y = 0; y < gridHeight; y ++)
		{
			for (int x = 0; x < gridWidth; x ++)
			{
				spots[x][y] = new Spot(new Point(x, y), this);
				add(spots[x][y]);
				if (spots[x][y].isPossibleEnd()) spots[x][y].setWindow(window);
			}
		}
		addPieces();
	}

	private void createPawns(int row, int color)
	{
		int delta, start;
		delta = gridWidth - REGULAR_SIZE;
		start = zero(REGULAR_SIZE / 2 + delta / 2);
		for (int x = start; x > start - REGULAR_SIZE / 2; x --)
		{
			spots[x][row].createPiece(Piece.PAWN, color);
		}
		start ++;
		for (int x = start; x < start + REGULAR_SIZE / 2; x ++)
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

	public int getBottomRow()
	{
		return gridHeight - 1;
	}

	public int getTopRow()
	{
		return 0;
	}

	public MoveHistory getMoveHistory()
	{
		return moveHistory;
	}

	public Spot getSpot(IntPiece spot)
	{
		if (spot == null) return null;
		return getSpot(spot.location);
	}

	public Spot getSpot(Point location)
	{
		if (location == null) return null;
		return getSpot(location.x, location.y);
	}

	public Spot getSpot(int x, int y)
	{
		if (spotExist(x, y)) return spots[x][y];
		return null;
	}

	public Boolean hasUndo()
	{
		return moveHistory.hasUndo();
	}

	public Boolean hasRedo()
	{
		return moveHistory.hasRedo();
	}

	public void undo()
	{
		deselect();
		moveHistory.undo(this);
	}

	public void updateControls()
	{
		options.updateControls();
	}

	public void redo()
	{
		deselect();
		moveHistory.redo(this);
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
	}

	public void reset()
	{
		reset(gridWidth, gridHeight);
	}

	public void reset(int width, int height)
	{
		removeGrid();
		createGrid(width, height);
		moveHistory = new MoveHistory();
		turn = 0;
		draw();
	}

	public void setOptions(Options value)
	{
		options = value;
	}

	private Boolean spotExist(int x, int y)
	{
		return x >= 0 && x < gridWidth && y >= 0 && y < gridHeight;
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
		if (i <= 0) return 0;
		return i - 1;
	}
}
