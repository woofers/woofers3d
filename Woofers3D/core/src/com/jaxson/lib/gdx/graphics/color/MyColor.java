package com.jaxson.lib.gdx.graphics.color;

import com.badlogic.gdx.graphics.Color;
import com.jaxson.lib.math.MyMath;
import com.jaxson.lib.util.Printer;

public class MyColor extends Color
{
	public static final int MIN_VALUE_INT = 0;
	public static final int MAX_VALUE_INT = 255;
	public static final float MIN_VALUE_FLOAT = 0f;
	public static final float MAX_VALUE_FLOAT = 1f;
	public static final float RGB_TO_FLOAT = MAX_VALUE_FLOAT / MAX_VALUE_INT;
	public static final int FLOAT_TO_RGB = MAX_VALUE_INT;

	public MyColor()
	{
		this(BLACK);
	}

	public MyColor(Color color)
	{
		super(color);
	}

	public MyColor(float r, float g, float b)
	{
		this(r, g, b, MAX_VALUE_FLOAT);
	}

	public MyColor(float r, float g, float b, float a)
	{
		super(r, g, b, a);
	}

	public MyColor(int r, int g, int b)
	{
		this(r, g, b, MAX_VALUE_INT);
	}

	public MyColor(int r, int g, int b, int a)
	{
		this(toFloat(r), toFloat(g), toFloat(b), toFloat(a));
	}

	public float alpha()
	{
		return a;
	}

	public int alphaInt()
	{
		return toInt(a);
	}

	public float blue()
	{
		return b;
	}

	public int blueInt()
	{
		return toInt(b);
	}

	public float green()
	{
		return g;
	}

	public int greenInt()
	{
		return toInt(g);
	}

	public MyColor random()
	{
		set(new RandomColor());
		return this;
	}

	public MyColor random(int min, int max)
	{
		set(new RandomColor(min, max));
		return this;
	}

	public MyColor random(int minR, int maxR,
			int minG, int maxG,
			int minB, int maxB)
	{
		set(new RandomColor(minR, maxR, minG, maxG, minB, maxB));
		return this;
	}

	public float red()
	{
		return r;
	}

	public int redInt()
	{
		return toInt(r);
	}

	@Override
	public String toString()
	{
		return new Printer(getClass(),
				new Printer.Label("Red", redInt()),
				new Printer.Label("Green", greenInt()),
				new Printer.Label("Blue", blueInt())).toString();
	}

	private static float toFloat(int channel)
	{
		channel = MyMath.max(MIN_VALUE_INT, channel);
		channel = MyMath.min(MAX_VALUE_INT, channel);
		return channel * RGB_TO_FLOAT;
	}

	private static int toInt(float channel)
	{
		return (int) (channel * FLOAT_TO_RGB);
	}
}
