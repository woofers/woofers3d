package com.jaxson.board;

import com.jaxson.ui.Panel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Square extends Panel
{
	//private Point location;
	private Boolean isEmtpy;
	private Square leftTop, rightTop, leftBottom, rightBotton;

	public Square(Color color)
	{
		super();
		setBackground(color);
		addMouseListener(new MyMouseAdapter<Square>(this));
	}

	public Color getPlayer()
	{
		return null;
	}
}

class MyMouseAdapter<T extends Panel> extends MouseAdapter
{
	private T object;

	public MyMouseAdapter(T object)
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
