package com.jaxson.lib.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A Math class for miscellaneous functions.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class MyMath
{
	public static final float HALF = 1f / 2f;
	public static final float DIAMETER_TO_RADIUS = HALF;
	public static final int FLIP_SIGN = -1;
	public static final float GRAVITY_EARTH = 9.80665f;

	/**
	 * Get the absoulute value of the number.
	 * @param value Number The number
	 * @return {@link float} - The absoulute value
	 */
	public static float abs(float value)
	{
		return Math.abs(value);
	}

	/**
	 * Inverts the sign of a number.
	 * @param value The number
	 * @return {@link float} - The inverted value
	 */
	public static float flipSign(float value)
	{
		return value * FLIP_SIGN;
	}

	/**
	 * Returns the current Random instance.
	 * @return {@link Random} - The Random instance
	 */
	public static Random getRandom()
	{
		return ThreadLocalRandom.current();
	}

	/**
	 * Retruns the whether a number is even.
	 * @param value The number
	 * @return {@link boolean} - Whether the number is even
	 */
	public static boolean isEven(int value)
	{
		return value % 2 == 0;
	}

	/**
	 * Retruns the whether a number is odd.
	 * @param value The number
	 * @return {@link boolean} - Whether the number is odd
	 */
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

	public static float max(float a, float b)
	{
		return Math.max(a, b);
	}

	public static float min(float a, float b)
	{
		return Math.min(a, b);
	}

	public static float randFloat(float min, float max)
	{
		return getRandom().nextFloat() * (max - min + 1) + min;
	}

	public static int randInt(int min, int max)
	{
		return getRandom().nextInt(max - min + 1) + min;
	}

	public static float reciprocal(float value)
	{
		return 1f / value;
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
