package com.jaxson.lib.gdx.graphics;

import com.badlogic.gdx.graphics.Color;
import com.jaxson.lib.util.MyMath;

public class MyColor extends Color
{
	private static final int ALPHA = 255;

	public MyColor(int r, int g, int b)
	{
		this(r, g, b, ALPHA);
	}

	public MyColor(int r, int g, int b, int a)
	{
		super(MyMath.toRGB(r), MyMath.toRGB(g), MyMath.toRGB(b), MyMath.toRGB(a));
	}
}
