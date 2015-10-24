package com.jaxson.ai;

import com.jaxson.geom.Point;

public abstract class Player
{
	private int color;
	private String name;

	public Player(int color)
	{
		this(color, "Generic Computer");
	}

	public Player(int color, String name)
	{
		this.color = color;
		this.name = name;
	}

	public abstract void move(Point location);

	public int getColor()
	{
		return color;
	}

	public String getName()
	{
		return name;
	}

	@Override
	public String toString()
	{
		return name + "playing as " + color;
	}

}
