package com.jaxson.lib.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MyMath
{
	public static final float DIAMETER_TO_RADIUS = 1f / 2f;

	public static Random getRandom()
	{
		return ThreadLocalRandom.current();
	}

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

	public static float randFloat(float min, float max)
	{
		return getRandom().nextFloat() * (max - min + 1) + min;
	}

	public static int randInt(int min, int max)
	{
		return getRandom().nextInt(max - min + 1) + min;
	}

	public static int toInt(double value)
	{
		return (int) value;
	}

	public static int toInt(float value)
	{
		return (int) value;
	}
}
