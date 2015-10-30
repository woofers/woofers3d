package com.jaxson.ui.board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.jaxson.board.IntBoard;
import com.jaxson.board.IntPiece;
import com.jaxson.board.move.Move;
import com.jaxson.board.move.MoveList;
import com.jaxson.geom.Point;
import com.jaxson.ui.Panel;
import com.jaxson.ui.Window;
import com.jaxson.util.MyArrayList;

public class Spot<T extends Window> extends Panel
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
	private T window;

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
		MoveList moves = new MoveList();
		IntBoard intBoard = board.toIntBoard();
		Spot spot;
		moves = intBoard.getLegalMoves(toIntPiece());
		System.out.println(moves.toString());
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

	private Color getColor()
	{
		if (isEven(location.y))
		{
			if (isEven(location.x))
			{
				return DARKCOLOR;
			}
			return LIGHTCOLOR;
		}
		if (isEven(location.x))
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
				if (location.y == board.gridHeight - 1)
				{
					return true;
				}
			}
			else
			{
				if (location.y == 0)
				{
					return true;
				}
			}
		}
		return false;
	}

	private Boolean isEven(int i)
	{
		return i % 2 == 0;
	}

	private Boolean isMoveSelected()
	{
		Color color = getBackground();
		return color == MOVECOLOR || color == HOLDMOVECOLOR;
	}

	public Boolean isPossibleEnd()
	{
		return location.y == board.gridHeight - 1 || location.y == 0;
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
		board.turn ++;
		move.move(board);
		board.getMoveHistory().add(move);
		move = null;
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
		if (isEmpty())
		{
			return;
		}
		remove(piece);
		piece = null;
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
		if (isPromotable())
		{
			piece = newPiece.promote(window);
		}
		if (piece != null)
		{
			piece.turn ++;
			add(piece);
		}
		draw();
	}

	public void setMove(Move value)
	{
		move = value;
	}

	public void setWindow(T value)
	{
		window = value;
	}

	public IntPiece toIntPiece()
	{
		if (isEmpty())
		{
			return new IntPiece(location);
		}
		return piece.toIntPiece(location);
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
