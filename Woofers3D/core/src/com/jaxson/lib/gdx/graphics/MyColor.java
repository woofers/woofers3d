package com.jaxson.lib.gdx.graphics;

import com.badlogic.gdx.graphics.Color;
import com.jaxson.lib.gdx.util.GdxMath;

public class MyColor extends Color
{
	public static final int MIN_VALUE_INT = 0;
	public static final int MAX_VALUE_INT = 255;
	public static final float MIN_VALUE_FLOAT = 0f;
	public static final float MAX_VALUE_FLOAT = 1f;
	public static final float RGB_TO_FLOAT = MAX_VALUE_FLOAT / MAX_VALUE_INT;

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
		this(toInt(r), toInt(g), toInt(b), toInt(a));
	}

	public void rand()
	{
		set(GdxMath.randColor());
	}

	public void rand(int min, int max)
	{
		set(GdxMath.randColor(min, max));
	}

	public static float toInt(int channel)
	{
		channel = GdxMath.max(MIN_VALUE_INT, channel);
		channel = GdxMath.min(MAX_VALUE_INT, channel);
		return channel * RGB_TO_FLOAT;
	}
}
