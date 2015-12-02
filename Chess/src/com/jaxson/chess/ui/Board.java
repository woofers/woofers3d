package com.jaxson.chess.ui;

import java.awt.GridLayout;

import com.jaxson.chess.ai.EasyPlayer;
import com.jaxson.chess.ai.HardPlayer;
import com.jaxson.chess.board.IntBoard;
import com.jaxson.chess.board.IntPiece;
import com.jaxson.chess.board.move.MoveHistory;
import com.jaxson.lib.geom.Point;
import com.jaxson.lib.ui.Panel;
import com.jaxson.lib.ui.Window;

public class Board extends Panel
{
	public static final int DEFAULT_COLOR = Piece.WHITE;
	private static final int REGULAR_SIZE = 8;

	private int width, height, turn, color;
	private ChessWindow window;
	private MoveHistory moveHistory;
	private Spot[][] board;
	private Options options;

	public Board(ChessWindow window)
	{
		super();
		this.window = window;
		this.moveHistory = new MoveHistory();
		reset(REGULAR_SIZE, REGULAR_SIZE, true);

		toIntBoard();
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
		delta = width - REGULAR_SIZE;
		queen = zero(REGULAR_SIZE / 2 + delta / 2);
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

	private void createGrid(int width, int height)
	{
		if (width <= 0 && height <= 0) return;
		this.width = width;
		this.height = height;
		setLayout(new GridLayout(height, width));
		board = new Spot[width][height];
		for (int y = 0; y < height; y ++)
		{
			for (int x = 0; x < width; x ++)
			{
				board[x][y] = new Spot(new Point(x, y), this);
				add(board[x][y]);
				if (board[x][y].isPossibleEnd()) board[x][y].setWindow(window);
			}
		}
		addPieces();
		draw();
	}

	private void createPawns(int row, int color)
	{
		int delta, start;
		delta = width - REGULAR_SIZE;
		start = zero(REGULAR_SIZE / 2 + delta / 2);
		for (int x = start; x > start - REGULAR_SIZE / 2; x --)
		{
			board[x][row].createPiece(Piece.PAWN, color);
		}
		start ++;
		for (int x = start; x < start + REGULAR_SIZE / 2; x ++)
		{
			board[x][row].createPiece(Piece.PAWN, color);
		}
	}

	public void deselect()
	{
		for (Spot[] row: board)
		{
			for (Spot spot: row)
			{
				spot.deselect();
			}
		}
	}

	public int getBottomRow()
	{
		return height - 1;
	}

	public int getTopRow()
	{
		return 0;
	}

	public MoveHistory getMoveHistory()
	{
		return moveHistory;
	}

	public int getColor()
	{
		return color;
	}

	public int getBoardHeight()
	{
		return height;
	}

	public int getTurn()
	{
		return turn;
	}

	public int getBoardWidth()
	{
		return width;
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
		if (spotExist(x, y)) return board[x][y];
		return null;
	}

	public Boolean hasComputer()
	{
		return options.hasComputer();
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
		for (int y = 0; y < height; y ++)
		{
			for (int x = 0; x < width; x ++)
			{
				remove(board[x][y]);
				board[x][y] = null;
			}
		}
		draw();
	}

	public void reset()
	{
		reset(width, height);
	}

	public void reset(int width, int height)
	{
		reset(width, height, false);
	}

	public void reset(int width, int height, Boolean isInit)
	{
		color = DEFAULT_COLOR;
		turn = 0;
		moveHistory.clear();
		if (!isInit) removeGrid();
		createGrid(width, height);
	}

	public void setTurn(int turn)
	{
		this.turn = turn;
	}

	public void setOptions(Options options)
	{
		this.options = options;
	}

	public void swapColors()
	{
		color = Piece.getOppositeColor(color);
	}

	private Boolean spotExist(int x, int y)
	{
		return x >= 0 && x < width && y >= 0 && y < height;
	}

	public IntBoard toIntBoard()
	{
		return new IntBoard(this);
	}

	private int zero(int i)
	{
		if (i <= 0) return 0;
		return i - 1;
	}
}
