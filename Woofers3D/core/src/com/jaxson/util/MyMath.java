package com.jaxson.util;

import java.lang.Math;

public class MyMath
{
	public static double log(double value, double base)
	{
		return Math.log(value) / Math.log(base);
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
