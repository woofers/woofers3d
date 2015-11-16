package com.jaxson.util;

import java.lang.Math;

public class MyMath extends Math
{
	public static double log(double value, double base)
	{
		return log(value) / log(base);
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
