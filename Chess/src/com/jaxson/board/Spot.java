package com.jaxson.board;

import com.jaxson.board.containers.*;
import com.jaxson.ui.Panel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.util.ArrayList;

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
	private Color color;
	private Spot transferSpot;
	private Boolean hasMoved = false;

	public Spot(Point location, Board board)
	{
		super(new BorderLayout());
		this.board = board;
		this.location = location;
		color = getColor(location);
		deselect();
		addMouseListener(new MyMouseAdapter(this));
	}

	private Color getColor(Point spot)
	{
		if (isEven(spot.y))
		{
			if (isEven(spot.x))
			{
				return DARKCOLOR;
			}
			return LIGHTCOLOR;
		}
		if (isEven(spot.x))
		{
			return LIGHTCOLOR;
		}
		return DARKCOLOR;
	}

	private Boolean isEven(int i)
	{
		return i % 2 == 0;
	}

	private void select()
	{
		if (!isEmpty())
		{
			setBackground(SELECTEDCOLOR);
			displayMoves();
		}
	}

	private void holdSelect()
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
		hasMoved = true;
	}

	private void move()
	{
		setPiece(transferSpot.getPiece());
		transferSpot.removePiece();
	}

	public void deselect()
	{
		setBackground(color);
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

	public void setPiece(Piece piece)
	{
		removePiece();
		this.piece = piece;
		add(piece);
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
		return new IntPiece(piece.color, piece.type, location, hasMoved);
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
		}
		else if (isMoveSelected())
		{
			move();
			deselectAll();
		}
	}
}

class MyMouseAdapter extends MouseAdapter
{
	private Spot object;

	public MyMouseAdapter(Spot object)
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
