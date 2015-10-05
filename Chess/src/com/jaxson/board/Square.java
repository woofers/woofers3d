package com.jaxson.board;

import com.jaxson.ui.Panel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Square extends Panel
{
	private Piece piece;

	public Square(Color color)
	{
		super(new BorderLayout());
		setBackground(color);
		addMouseListener(new MyMouseAdapter(this));
		createPiece(Piece.KING, Piece.WHITE);
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
	private Square object;

	public MyMouseAdapter(Square object)
	{
		this.object = object;
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		object.setBackground(Color.BLUE);
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		object.setBackground(Color.YELLOW);
	}
}
