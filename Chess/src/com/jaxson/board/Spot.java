package com.jaxson.board;

import com.jaxson.ui.Panel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Spot extends Panel
{
	private static final Color SELECTEDCOLOR = new Color(43, 177, 94);
	private static final Color HOLDSELECTEDCOLOR = SELECTEDCOLOR.darker();
	private static final Color MOVECOLOR = new Color(224, 65, 100);
	private static final Color HOLDMOVECOLOR = MOVECOLOR.darker();

	private Board board;
	private Piece piece;
	private Color color;
	private Spot leftSpot, rightSpot, topSpot, bottomSpot;

	public Spot(Color color, Board board)
	{
		super(new BorderLayout());
		this.board = board;
		this.color = color;
		setBackground(color);
		addMouseListener(new MyMouseAdapter(this));
	}

	public void setLink(Spot leftSpot, Spot rightSpot, Spot topSpot, Spot bottomSpot)
	{
		this.leftSpot = leftSpot;
		this.rightSpot = rightSpot;
		this.topSpot = topSpot;
		this.bottomSpot = bottomSpot;
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
		Spot[] legalMoves = getLegalMoves();
		for (int i = 0; i < legalMoves.length; i ++)
		{
			if (legalMoves[i] != null)
			{
				if (legalMoves[i].isEmpty())
				{

				}
				legalMoves[i].moveSelect();
			}
		}
	}

	private void hideMoves()
	{
		Spot[] legalMoves = getLegalMoves();
		for (int i = 0; i < legalMoves.length; i ++)
		{
			if (legalMoves[i] != null)
			{
				legalMoves[i].deselect();
			}
		}
	}

	private Spot[] getLegalMoves()
	{
		Spot[] moves = new Spot[7];

		switch (piece.type)
		{
			case Piece.KING:
				moves = getSurrondingSpots();
				break;
			case Piece.QUEEN:
				break;
			case Piece.ROOK:

				break;
			case Piece.BISHOP:
				break;
			case Piece.KNIGHT:
				break;
			case Piece.PAWN:
				break;
			default:
				break;
		}
		return moves;
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

	private Spot[] getSurrondingSpots()
	{
		Spot[] spots = new Spot[8];
		spots[0] = getTopLeft();
		spots[1] = getTopMiddle();
		spots[2] = getTopRight();
		spots[3] = getMiddleLeft();
		spots[4] = getMiddleRight();
		spots[5] = getBottomLeft();
		spots[6] = getBottomMiddle();
		spots[7] = getBottomRight();
		return spots;
	}

	public Spot[] getAllTopLeft()
	{
		return null;
	}

	public Spot[] getAllTopMiddle()
	{
		return null;
	}

	public Spot[] getAllTopRight()
	{
		return null;
	}

	public Spot[] getAllMiddleLeft()
	{
		return null;
	}

	public Spot[] getAllMiddleRight()
	{
		return null;
	}

	public Spot[] getAllBottomLeft()
	{
		return null;
	}

	public Spot[] getAllBottomMiddle()
	{
		return null;
	}

	public Spot[] getAllBottomRight()
	{
		return null;
	}

	public Spot getTopLeft()
	{
		if (topSpot == null)
		{
			return null;
		}
		return topSpot.getMiddleLeft();
	}

	public Spot getTopMiddle()
	{
		return topSpot;
	}

	public Spot getTopRight()
	{
		if (topSpot == null)
		{
			return null;
		}
		return topSpot.getMiddleRight();
	}

	public Spot getMiddleLeft()
	{
		return leftSpot;
	}

	public Spot getMiddleRight()
	{
		return rightSpot;
	}

	public Spot getBottomLeft()
	{
		if (bottomSpot == null)
		{
			return null;
		}
		return bottomSpot.getMiddleLeft();
	}

	public Spot getBottomMiddle()
	{
		return bottomSpot;
	}

	public Spot getBottomRight()
	{
		if (bottomSpot == null)
		{
			return null;
		}
		return bottomSpot.getMiddleRight();
	}

	public int toInt()
	{
		if (isEmpty())
		{
			return 0;
		}
		return joinInt(piece.color, piece.type);
	}

	private int joinInt(int a, int b)
	{
		String string = Integer.toString(a) + Integer.toString(b);
		return Integer.parseInt(string);
	}

	public int getType()
	{
		if (isEmpty())
		{
			return 0;
		}
		return piece.type;
	}

	public int getColor()
	{
		if (isEmpty())
		{
			return 0;
		}
		return piece.color;
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
