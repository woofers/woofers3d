package com.jaxson.lib.math;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A Math class for miscellaneous functions.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class MyMath
{
	/**
	 * Constant to invert a sign.
	 */
	public static final int FLIP_SIGN = -1;

	public static final float DEGREES_TO_RADIANS = Circle.PI / 180f;

	public static final float RADIANS_TO_DEGREES
			= new Reciprocal(DEGREES_TO_RADIANS).floatValue();

	/**
	 * Acceleration due to graviy on Earth.
	 */
	public static final float GRAVITY_EARTH = 9.80665f;

	protected MyMath()
	{

	}

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
	 * Returns the greater of two values.
	 * @param a Number
	 * @param b Number
	 * @return {@link float} - Max value
	 */
	public static int max(int a, int b)
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
	 * Returns the smaller of two values.
	 * @param a Number
	 * @param b Number
	 * @return {@link int} - Min value
	 */
	public static int min(int a, int b)
	{
		return Math.min(a, b);
	}
}
