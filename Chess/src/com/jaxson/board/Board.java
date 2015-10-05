package com.jaxson.board;

import com.jaxson.ui.Panel;
import java.awt.Color;
import java.awt.GridLayout;

public class Board extends Panel
{
	private int gridWidth, gridHeight;
	private Square[][] squares;
	private Color[] colors;

	public Board()
	{
		super();

		colors = new Color[2];
		colors[0] = new Color(209, 139, 71);
		colors[1] = new Color(255, 206, 158);
		gridWidth = 0;
		gridHeight = 0;
	}

	public void createGrid(int width, int height)
	{
		gridWidth = width;
		gridHeight = height;

		setLayout(new GridLayout(gridWidth, gridHeight));
		squares = new Square[gridWidth][gridHeight];
		for (int x = 0; x < gridWidth; x ++)
		{
			for (int y = 0; y < gridHeight; y ++)
			{
				squares[x][y] = new Square(getColor(x, y));
				add(squares[x][y]);
			}
		}
		squares[0][0].draw();
		System.out.println(squares[0][0].getSize());
	}

	public void removeGrid()
	{
		for (int x = 0; x < gridWidth; x ++)
		{
			for (int y = 0; y < gridHeight; y ++)
			{
				remove(squares[x][y]);
				squares[x][y] = null;
			}
		}
		draw();
	}

	private Color getColor(int x, int y)
	{
		if (isEven(y))
		{
			if (isEven(x))
			{
				return colors[1];
			}
			return colors[0];
		}
		if (isEven(x))
		{
			return colors[0];
		}
		return colors[1];
	}

	private boolean isEven(int i)
	{
		return i % 2 == 0;
	}
}
