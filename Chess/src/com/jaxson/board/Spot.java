package com.jaxson.board;

import com.jaxson.board.containers.IntPiece;
import com.jaxson.board.containers.Point;
import com.jaxson.ui.Panel;
import com.jaxson.ui.Window;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.util.ArrayList;

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
	private Spot transferSpot;
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

	private Boolean isEven(int i)
	{
		return i % 2 == 0;
	}

	private Boolean isPromotable()
	{
		if (!isEmpty())
		{
			if (isEnd())
			{
				if (piece.type == Piece.PAWN)
				{
					if (piece.hasMoved)
					{
						return true;
					}
				}
			}
		}
		return false;
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

	public Boolean isPossibleEnd()
	{
		return location.y == board.gridHeight - 1 || location.y == 0;
	}

	public void select()
	{
		if (!isEmpty())
		{
			setBackground(SELECTEDCOLOR);
		}
	}

	public void holdSelect()
	{
		if (!isEmpty())
		{
			setBackground(HOLDSELECTEDCOLOR);
		}
	}

	private void moveSelect()
	{
		setBackground(MOVECOLOR);
	}

	private void holdMoveSelect()
	{
		setBackground(HOLDMOVECOLOR);
	}

	private void move()
	{
		setPiece(transferSpot.getPiece());
		transferSpot.removePiece();
	}

	public void deselect()
	{
		setBackground(getColor());
		transferSpot = null;
	}

	private void deselectAll()
	{
		board.deselect();
	}

	private Boolean isSelected()
	{
		Color color = getBackground();
		return color == SELECTEDCOLOR || color == HOLDSELECTEDCOLOR;
	}

	private Boolean isMoveSelected()
	{
		Color color = getBackground();
		return color == MOVECOLOR || color == HOLDMOVECOLOR;
	}

	private void displayMoves()
	{
		ArrayList<IntPiece> moves = new ArrayList<>();
		IntBoard intBoard = board.toIntBoard();
		Spot spot;
		moves = intBoard.getLegalMoves(toIntPiece());
		for (IntPiece move: moves)
		{
			spot = board.getSpot(move.location.x, move.location.y);
			spot.setTransferSpot(this);
			spot.moveSelect();
		}
		if (moves.isEmpty())
		{
			deselect();
		}
	}

	public void setWindow(T window)
	{
		this.window = window;
	}

	public void createPiece(int type, int color)
	{
		removePiece();
		piece = new Piece(type, color);
		add(piece);
	}

	public Piece getPiece()
	{
		return piece;
	}

	public void setPiece(Piece newPiece)
	{
		removePiece();
		piece = newPiece;
		piece.hasMoved = true;
		add(piece);
		if (isPromotable())
		{
			removePiece();
			piece = newPiece.promote(window);
			add(piece);
		}
		draw();
	}

	public void setTransferSpot(Spot spot)
	{
		transferSpot = spot;
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

	private Boolean isEmpty()
	{
		return piece == null;
	}

	public IntPiece toIntPiece()
	{
		if (isEmpty())
		{
			return new IntPiece(location);
		}
		return new IntPiece(piece.color, piece.type, location, piece.direction, piece.hasMoved);
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
