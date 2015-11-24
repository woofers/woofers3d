package com.jaxson.lib.geom;

public class Point
{
	private static final String X_PREFIX  = "X: ";
	private static final String Y_PREFIX  = "Y: ";
	private static final String SEPARATOR = ", ";

	public int x, y;

	public Point()
	{
		this(0, 0);
	}

	public Point(Point point)
	{
		this(point.x, point.y);
	}

	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public Point clone()
	{
		return new Point(this);
	}

	@Override
	public String toString()
	{
		return X_PREFIX + Integer.toString(x) + SEPARATOR + Y_PREFIX + Integer.toString(y);
	}
}
