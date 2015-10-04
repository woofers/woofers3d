package com.jaxson.board;

import com.jaxson.ui.Panel;
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
		super();
		setBackground(color);
		addMouseListener(new MyMouseAdapter(this));
		piece = new Piece(Piece.KING, Piece.WHITE);
		// /add(piece);
	}

	public Color getPlayer()
	{
		return null;
	}

	public Boolean isEmpty()
	{
		return null;
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
