package com.jaxson.board.containers;

public class Point
{
	public int x;
	public int y;

	public Point()
	{
		this(0, 0);
	}

	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public String toString()
	{
		return "X: " + Integer.toString(x) + ", Y: " + Integer.toString(y);
	}
}