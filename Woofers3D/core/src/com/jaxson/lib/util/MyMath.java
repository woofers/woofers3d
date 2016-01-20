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
	public static final int FLIP_SIGN = -1;

	public static final float DIAMETER_TO_RADIUS = HALF;

	/**
	 * Acceleration due to graviy on Earth.
	 */
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

	/**
	 * Retruns base E log.
	 * @param value The number
	 * @return {@link double} - Result
	 */
	public static double log(double value)
	{
		return log(value, Math.E);
	}

	/**
	 * Retruns base N log.
	 * @param value The number
	 * @param base The base
	 * @return {@link double} - Result
	 */
	public static double log(double value, double base)
	{
		return Math.log10(value) / Math.log10(base);
	}

	/**
	 * Returns the greater of two values.
	 * @param a Number
	 * @param b Number
	 * @return {@link float} - Max value
	 */
	public static float max(float a, float b)
	{
		return Math.max(a, b);
	}

	/**
	 * Returns the smaller of two values.
	 * @param a Number
	 * @param b Number
	 * @return {@link float} - Min value
	 */
	public static float min(float a, float b)
	{
		return Math.min(a, b);
	}

	/**
	 * Returns random {@link float} between the range.
	 * @param min Min
	 * @param max Max
	 * @return {@link float} - Random float
	 */
	public static float randFloat(float min, float max)
	{
		return getRandom().nextFloat() * (max - min + 1) + min;
	}

	/**
	 * Returns random {@link int} between the range.
	 * @param min Min
	 * @param max Max
	 * @return {@link int} - Random int
	 */
	public static int randInt(int min, int max)
	{
		return getRandom().nextInt(max - min + 1) + min;
	}

	/**
	 * Returns the reciprocal of an {@link float}.
	 * @param value Number
	 * @return {@link int} - Reciprocal
	 */
	public static float reciprocal(float value)
	{
		return 1f / value;
	}
}
