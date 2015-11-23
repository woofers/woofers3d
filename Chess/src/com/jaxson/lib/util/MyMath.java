package com.jaxson.lib.util;

import java.lang.Math;

public class MyMath
{
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

	public static double log10(double value)
	{
		return log(value, 10);
	}
}
