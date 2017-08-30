package com.jaxson.lib.gdx.graphics.color;

import com.badlogic.gdx.graphics.Color;
import com.jaxson.lib.math.random.RandomNumber;

public class RandomColor extends MyColor
{
	public RandomColor()
	{
		this(MIN_VALUE_INT, MAX_VALUE_INT);
	}

	public RandomColor(Color minColor, Color maxColor)
	{
		super(new RandomNumber(minColor.r, maxColor.r).floatValue(),
				new RandomNumber(minColor.g, maxColor.g).floatValue(),
				new RandomNumber(minColor.b, maxColor.b).floatValue());
	}

	public RandomColor(int minRGB, int maxRGB)
	{
		this(minRGB, maxRGB, minRGB, maxRGB, minRGB, maxRGB);
	}

	public RandomColor(int minR,
			int maxR,
			int minG,
			int maxG,
			int minB,
			int maxB)
	{
		super(new RandomNumber(minR, maxR).intValue(),
				new RandomNumber(minG, maxG).intValue(),
				new RandomNumber(minB, maxB).intValue());
	}
}
