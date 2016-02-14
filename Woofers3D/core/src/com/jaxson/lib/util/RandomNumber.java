package com.jaxson.lib.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomNumber extends Number
{
	private static int DEFAULT_MIN = 0;
	private static int DEFAULT_MAX = 100;
	private static String MIN_EXCEEDS_MAX = "Min cannot be greather or equal to max";

	private Number min;
	private Number max;
	private Number[] exclude;

	public RandomNumber()
	{
		this(DEFAULT_MIN, DEFAULT_MAX);
	}

	public RandomNumber(Number min, Number max)
	{
		this(min, max, (Number) null);
	}

	public RandomNumber(Number min, Number max, Number... exclude)
	{
		if (max.floatValue() < min.floatValue()) throw new IllegalArgumentException(MIN_EXCEEDS_MAX);
		this.min = min;
		this.max = max;
		this.exclude = exclude;
	}

	@Override
	public double doubleValue()
	{
		double value = getRandom().nextDouble() * (getMax().doubleValue() - getMin().doubleValue() + 1) + getMin().doubleValue();
		if (isExcluded(value)) return doubleValue();
		return value;
	}

	@Override
	public float floatValue()
	{
		float value = getRandom().nextFloat() * (getMax().floatValue() - getMin().floatValue() + 1) + getMin().floatValue();
		if (isExcluded(value)) return floatValue();
		return value;
	}

	public Number[] getExclude()
	{
		return exclude;
	}

	public Number getMax()
	{
		return max;
	}

	public Number getMin()
	{
		return min;
	}

	@Override
	public int intValue()
	{
		int value = getRandom().nextInt(getMax().intValue() - getMin().intValue() + 1) + getMin().intValue();
		if (isExcluded(value)) return intValue();
		return value;
	}

	public boolean isExcluded(Number value)
	{
		if (value.floatValue() < getMin().floatValue() || value.floatValue() > getMax().floatValue()) return false;
		for (Number number: getExclude())
		{
			if (value.equals(number)) return true;
		}
		return false;
	}

	@Override
	public long longValue()
	{
		long value = getRandom().nextLong() * (getMax().longValue() - getMin().longValue() + 1) + getMin().longValue();
		if (isExcluded(value)) return longValue();
		return value;
	}

	/**
	 * Returns the current Random instance.
	 * @return {@link Random} - The Random instance
	 */
	private static Random getRandom()
	{
		return ThreadLocalRandom.current();
	}
}
