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
	private static final Color selectedColor = new Color(43, 177, 94);
	private static final Color holdColor = selectedColor.darker();

	public Board board;
	private Piece piece;
	private Color color;

	public Spot(Color color, Board board)
	{
		super(new BorderLayout());
		this.board = board;
		this.color = color;
		setBackground(color);
		addMouseListener(new MyMouseAdapter(this));
	}

	public void toggleSelect()
	{
		if (isSelected())
		{
			deselect();
		}
		else
		{
			select();
		}
	}

	public void select()
	{
		if (!isEmpty())
		{
			setBackground(selectedColor);
		}
	}

	public void holdSelect()
	{
		if (!isEmpty())
		{
			setBackground(holdColor);
		}
	}

	public void deselect()
	{
		setBackground(color);
	}

	public Boolean isSelected()
	{
		return getBackground() == selectedColor || getBackground() == holdColor;
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
		object.board.deselect();
		object.holdSelect();
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		object.select();
	}
}
