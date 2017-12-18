package com.jaxson.lib.math.random;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import com.jaxson.lib.math.ComparableNumber;

/**
 * An immutable {@link Object} used to define the a random number.
 * @author Jaxson Van Doorn
 * @since 1.0
 */
public class RandomNumber extends ComparableNumber
{
	private static final long serialVersionUID = 3858351995041829520L;
	private static final int DEFAULT_MIN = 0;
	private static final int DEFAULT_MAX = 100;
	private static final String MIN_EXCEEDS_MAX
			= "Min cannot be greather or equal to max";

	private Number min;

	private Number max;
	private Number[] excluded;

	/**
	 * Constructs a {@link RandomNumber} between {@code 0} and {@code 100}.
	 */
	public RandomNumber()
	{
		this(DEFAULT_MIN, DEFAULT_MAX);
	}

	/**
	 * Construct a {@link RandomNumber} between the min and max.
	 * @param min The min value of the {@link Number}
	 * @param max The max value of the {@link Number}
	 */
	public RandomNumber(Number min, Number max)
	{
		this(min, max, (Number) null);
	}

	/**
	 * Construct a {@link RandomNumber} between the min and max.
	 * @param min The min value of the {@link Number}
	 * @param max The max value of the {@link Number}
	 * @param excluded Array of excluded {@link Number}s from the range
	 */
	public RandomNumber(Number min, Number max, Number... excluded)
	{
		this.min = min;
		this.max = max;
		this.excluded = excluded;
	}

	@Override
	public double doubleValue()
	{
		double value = getRandom().nextDouble();
		value *= max().doubleValue() - min().doubleValue();
		value += min().doubleValue();
		if (isExcluded(value)) return doubleValue();
		return value;
	}

	/**
	 * Gets the excluded range.
	 * @return {@link Number}[] - The rxcluded range
	 */
	public Number[] excluded()
	{
		return excluded;
	}

	@Override
	public float floatValue()
	{
		float value = getRandom().nextFloat();
		value *= max().floatValue() - min().floatValue();
		value += min().floatValue();
		if (isExcluded(value)) return floatValue();
		return value;
	}

	@Override
	public int intValue()
	{
		int value = getRandom().nextInt(
				max().intValue() - min().intValue() + 1);
		value += min().intValue();
		if (isExcluded(value)) return intValue();
		return value;
	}

	public boolean isExcluded(Number value)
	{
		for (Number number: excluded())
		{
			if (value.equals(number)) return true;
		}
		return false;
	}

	@Override
	public long longValue()
	{
		long value = getRandom().nextLong();
		value *= max().longValue() - min().longValue();
		value += min().longValue();
		if (isExcluded(value)) return longValue();
		return value;
	}

	/**
	 * Gets the max value of the range.
	 * @return {@link Number} - The max value of the range
	 */
	public Number max()
	{
		return max;
	}

	/**
	 * Gets the min value of the range.
	 * @return {@link Number} - The min value of the range
	 */
	public Number min()
	{
		return min;
	}

	/**
	 * Returns the current Random instance.
	 * @return {@link Random} - The Random instance
	 */
	public static ThreadLocalRandom getRandom()
	{
		return ThreadLocalRandom.current();
	}
}
