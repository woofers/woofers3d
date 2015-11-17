package com.jaxson.ui.board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.jaxson.board.IntBoard;
import com.jaxson.board.IntPiece;
import com.jaxson.board.move.Move;
import com.jaxson.board.move.MoveList;
import com.jaxson.board.move.MoveHistory;
import com.jaxson.geom.Point;
import com.jaxson.ui.board.ChessWindow;
import com.jaxson.ui.Panel;
import com.jaxson.ui.Window;
import com.jaxson.util.MyArrayList;
import com.jaxson.util.MyMath;

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
		if (board != null)
		{
			addMouseListener(new SpotMouseAdapter(this));
		}
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
		if (moves.isEmpty())
		{
			deselect();
		}
	}

	@Override
	public void draw()
	{
		if (piece != null) piece.draw();
		super.draw();
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

	private Spot getKingCastleSpot(int direction)
	{
		Spot spot = board.getSpot(location.x + 2, location.y);
		return spot;
	}

	public Piece getPiece()
	{
		return piece;
	}


	private void holdMoveSelect()
	{
		setBackground(HOLDMOVECOLOR);
	}

	public void holdSelect()
	{
		if (!isEmpty())
		{
			setBackground(HOLDSELECTEDCOLOR);
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
				if (location.y == board.getBottomRow())
				{
					return true;
				}
			}
			else
			{
				if (location.y == board.getTopRow())
				{
					return true;
				}
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
				if (piece.type == Piece.PAWN)
				{
					return true;
				}
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
		IntBoard intBoard = board.toIntBoard();
		//intBoard.aiMove().move(board);
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
			if (piece == null) return;
			if (piece.color != board.color) return;
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
		if (isEmpty())
		{
			return;
		}
		remove(piece);
		piece = null;
		draw();
	}

	public void select()
	{
		if (!isEmpty())
		{
			setBackground(SELECTEDCOLOR);
		}
	}

	public void setPiece(Piece newPiece)
	{
		removePiece();
		piece = newPiece;
		if (piece != null)
		{
			add(piece);
		}
		draw();
	}

	public void setMove(Move value)
	{
		move = value;
	}

	public void setWindow(ChessWindow value)
	{
		window = value;
	}

	public IntPiece toIntPiece()
	{
		if (isEmpty()) return new IntPiece(location);
		IntPiece newPiece = piece.toIntPiece(location);
		newPiece.boardHeight = board.getBottomRow();
		return newPiece;
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
