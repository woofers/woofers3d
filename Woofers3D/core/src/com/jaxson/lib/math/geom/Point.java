package com.jaxson.lib.math.geom;

public class Point
{
	private static final String X_PREFIX = "X: ";
	private static final String Y_PREFIX = ", Y: ";

	private int x, y;

	public Point()
	{
		this(0, 0);
	}

	public Point(int x, int y)
	{
		set(x, y);
	}

	public Point(Point point)
	{
		this(point.x, point.y);
	}

	@Override
	public Point clone()
	{
		return new Point(this);
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public Point set(int x, int y)
	{
		setX(x);
		setY(y);
		return this;
	}

	public Point setX(int x)
	{
		this.x = x;
		return this;
	}

	public Point setY(int y)
	{
		this.y = y;
		return this;
	}

	@Override
	public String toString()
	{
		return X_PREFIX + Integer.toString(x) + Y_PREFIX + Integer.toString(y);
	}
}
