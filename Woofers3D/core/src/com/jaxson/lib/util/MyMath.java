package com.jaxson.lib.util;

import java.lang.Math;

public class MyMath
{
	private static final int RGB_MAX        = 255;
	private static final float RGB_TO_FLOAT = (float)(1f) / (float)RGB_MAX;

	public static boolean isEven(int value)
	{
		return value % 2 == 0;
	}

	public static boolean isOdd(int value)
	{
		return !isEven(value);
	}

	public static double log(double value)
	{
		return log(value, Math.E);
	}

	public static double log(double value, double base)
	{
		return Math.log10(value) / Math.log10(base);
	}

	public static float toRGB(int color)
	{
		color = Math.abs(color);
		if (color > RGB_MAX) return RGB_TO_FLOAT;
		return color * RGB_TO_FLOAT;
	}

	public static int toInt(float value)
	{
		return (int)(value);
	}

	public static int toInt(double value)
	{
		return (int)(value);
	}
}
