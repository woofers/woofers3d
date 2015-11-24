package com.jaxson.chess.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.jaxson.chess.board.IntBoard;
import com.jaxson.chess.board.IntPiece;
import com.jaxson.chess.board.move.Move;
import com.jaxson.chess.board.move.MoveHistory;
import com.jaxson.chess.board.move.MoveList;
import com.jaxson.chess.ui.ChessWindow;
import com.jaxson.lib.geom.Point;
import com.jaxson.lib.ui.Panel;
import com.jaxson.lib.ui.Window;
import com.jaxson.lib.util.MyArrayList;
import com.jaxson.lib.util.MyMath;

public class Spot extends Panel
{
	private static final Color LIGHTCOLOR        = new Color(209, 139, 71);
	private static final Color DARKCOLOR         = new Color(255, 206, 158);
	private static final Color SELECTEDCOLOR     = new Color(43, 177, 94);
	private static final Color MOVECOLOR         = new Color(224, 65, 100);
	private static final Color HOLDSELECTEDCOLOR = SELECTEDCOLOR.darker();
	private static final Color HOLDMOVECOLOR     = MOVECOLOR.darker();

	private Board board;
	private Piece piece;
	private Point location;
	private Move move;
	private ChessWindow window;

	public Spot(Point location)
	{
		this(location, null);
	}

	public Spot(Point location, Board board)
	{
		super(new BorderLayout());
		this.board = board;
		this.location = location;
		deselect();
		if (board != null) addMouseListener(new SpotMouseAdapter(this));
	}

	public void createPiece(int type, int color)
	{
		removePiece();
		piece = new Piece(type, color);
		add(piece);
	}

	public void deselect()
	{
		setBackground(getColor());
		move = null;
	}

	private void deselectAll()
	{
		board.deselect();
	}

	private void displayMoves()
	{
		IntBoard intBoard = board.toIntBoard();
		MoveList moves = intBoard.getLegalMoves(toIntPiece());
		Spot spot;
		for (Move move: moves)
		{
			spot = board.getSpot(move.getOrigin());
			spot.setMove(move);
			spot.moveSelect();
		}
		if (moves.isEmpty()) deselect();
	}

	@Override
	public void draw()
	{
		if (piece != null) piece.draw();
		super.draw();
	}

	public int getBottomRow()
	{
		return board.getBottomRow();
	}

	private Color getColor()
	{
		if (MyMath.isEven(location.y))
		{
			if (MyMath.isEven(location.x))
			{
				return DARKCOLOR;
			}
			return LIGHTCOLOR;
		}
		if (MyMath.isEven(location.x))
		{
			return LIGHTCOLOR;
		}
		return DARKCOLOR;
	}

	public Piece getPiece()
	{
		return piece;
	}

	public Point getPieceLocation()
	{
		return location;
	}

	private void holdMoveSelect()
	{
		setBackground(HOLDMOVECOLOR);
	}

	public void holdSelect()
	{
		if (!isEmpty())
		{
			if (piece.color == board.getColor()) setBackground(HOLDSELECTEDCOLOR);
		}
	}

	private Boolean isEmpty()
	{
		return piece == null;
	}

	private Boolean isEnd()
	{
		if (!isEmpty())
		{
			if (piece.color == Piece.BLACK)
			{
				return location.y == board.getBottomRow();
			}
			else
			{
				return location.y == board.getTopRow();
			}
		}
		return false;
	}

	private Boolean isMoveSelected()
	{
		Color color = getBackground();
		return color == MOVECOLOR || color == HOLDMOVECOLOR;
	}

	public Boolean isPossibleEnd()
	{
		return location.y == board.getBottomRow() || location.y == board.getTopRow();
	}

	private Boolean isPromotable()
	{
		if (!isEmpty())
		{
			if (isEnd())
			{
				return piece.type == Piece.PAWN;
			}
		}
		return false;
	}

	private Boolean isSelected()
	{
		Color color = getBackground();
		return color == SELECTEDCOLOR || color == HOLDSELECTEDCOLOR;
	}

	private void move()
	{
		MoveHistory moveHistory = board.getMoveHistory();
		move.move(board);
		moveHistory.add(move);
		if (isPromotable())
		{
			move = new Move(toIntPiece(), piece.getPromotion(window, toIntPiece()));
			move.move(board);
			moveHistory.add(move);
		}
		board.updateControls();
		move = null;
		if (board.hasComputer())
		{
			Move newMove;
			IntBoard intBoard = board.toIntBoard();
			newMove = intBoard.aiMove();
			newMove.move(board);
			moveHistory.add(newMove);
		}
	}

	private void moveSelect()
	{
		setBackground(MOVECOLOR);
	}

	public void onCick()
	{
		if (isSelected())
		{
			deselectAll();
		}
		else if (isMoveSelected())
		{
			holdMoveSelect();
		}
		else
		{
			deselectAll();
			holdSelect();
		}
	}

	public void onRelease()
	{
		if (isSelected())
		{
			select();
			displayMoves();
		}
		else if (isMoveSelected())
		{
			move();
			deselectAll();
		}
	}

	public void removePiece()
	{
		if (isEmpty()) return;
		remove(piece);
		piece = null;
		draw();
	}

	public void select()
	{
		if (!isEmpty()) setBackground(SELECTEDCOLOR);
	}

	public void setPiece(Piece piece)
	{
		removePiece();
		this.piece = piece;
		if (piece != null) add(piece);
		draw();
	}

	public void setMove(Move move)
	{
		this.move = move;
	}

	public void setWindow(ChessWindow window)
	{
		this.window = window;
	}

	public IntPiece toIntPiece()
	{
		if (isEmpty()) return new IntPiece(location);
		return new IntPiece(this);
	}
}

class SpotMouseAdapter extends MouseAdapter
{
	private Spot object;

	public SpotMouseAdapter(Spot object)
	{
		this.object = object;
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		object.onCick();
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		object.onRelease();
	}
}
