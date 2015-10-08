package com.jaxson.board;

import com.jaxson.ui.Panel;
import com.jaxson.board.containers.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Spot extends Panel
{
	private static final Color LIGHTCOLOR = new Color(209, 139, 71);
	private static final Color DARKCOLOR  = new Color(255, 206, 158);
	private static final Color SELECTEDCOLOR = new Color(43, 177, 94);
	private static final Color HOLDSELECTEDCOLOR = SELECTEDCOLOR.darker();
	private static final Color MOVECOLOR = new Color(224, 65, 100);
	private static final Color HOLDMOVECOLOR = MOVECOLOR.darker();

	private Board board;
	private Piece piece;
	private Point location;
	private Color color;

	public Spot(Point location, Board board)
	{
		super(new BorderLayout());
		this.board = board;
		this.location = location;
		color = getColor(location);
		setBackground(color);
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

	private boolean isEven(int i)
	{
		return i % 2 == 0;
	}

	public void select()
	{
		if (!isEmpty())
		{
			setBackground(SELECTEDCOLOR);
			displayMoves();
		}
	}

	public void holdSelect()
	{
		if (!isEmpty())
		{
			setBackground(HOLDSELECTEDCOLOR);
		}
	}

	public void moveSelect()
	{
		setBackground(MOVECOLOR);
	}

	public void holdMoveSelect()
	{
		setBackground(HOLDMOVECOLOR);
	}

	public void deselect()
	{
		setBackground(color);
	}

	public Boolean isSelected()
	{
		Color color = getBackground();
		return color == SELECTEDCOLOR || color == HOLDSELECTEDCOLOR;
	}

	public Boolean isMoveSelected()
	{
		Color color = getBackground();
		return color == MOVECOLOR || color == HOLDMOVECOLOR;
	}

	private void displayMoves()
	{
		IntBoard intBoard = new IntBoard(board);
		intBoard.displayMoves(toIntPiece());
	}

	private void hideMoves()
	{

	}

	public void createPiece(int type, int color)
	{
		removePiece();
		piece = new Piece(type, color);
		add(piece);
	}

	public void setPiece(Piece piece)
	{
		removePiece();
		this.piece = piece;
		add(piece);
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

	public Boolean isEmpty()
	{
		return piece == null;
	}

	public IntPiece toIntPiece()
	{
		if (isEmpty())
		{
			return new IntPiece();
		}
		return new IntPiece(piece.color, piece.type, location);
	}

	public void onCick()
	{
		if (isSelected())
		{
			board.deselect();
		}
		else if (isMoveSelected())
		{
			holdMoveSelect();
		}
		else
		{
			board.deselect();
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
			moveSelect();
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
