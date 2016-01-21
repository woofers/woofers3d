package com.jaxson.lib.gdx.graphics;

import com.badlogic.gdx.graphics.Color;
import com.jaxson.lib.gdx.util.GdxMath;

public class MyColor extends Color
{
	private static final int ALPHA = GdxMath.RGB_MAX;

	public MyColor(int r, int g, int b)
	{
		this(r, g, b, ALPHA);
	}

	public MyColor(int r, int g, int b, int a)
	{
		super(GdxMath.toRGB(r), GdxMath.toRGB(g), GdxMath.toRGB(b), GdxMath.toRGB(a));
	}

	public void rand()
	{
		set(GdxMath.randColor());
	}

	public void rand(int min, int max)
	{
		set(GdxMath.randColor(min, max));
	}
}
