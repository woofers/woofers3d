package com.jaxson.board;

import com.jaxson.ui.Panel;
import java.awt.Color;
import java.awt.GridLayout;

public class Board extends Panel
{
	private static final int rowSize = 8;

	private Square[][] squares;
	private Color[] colors;

	public Board()
	{
		super(new GridLayout(rowSize, rowSize));

		colors = new Color[2];
		colors[0] = Color.WHITE;
		colors[1] = Color.BLACK;

		squares = new Square[rowSize][rowSize];
		for (int x = 0; x < rowSize; x ++)
		{
			for (int y = 0; y < rowSize; y ++)
			{
				squares[x][y] = new Square(getColor(x, y));
				add(squares[x][y]);
			}
		}
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
