package com.jaxson.lib.math;

import com.jaxson.lib.util.MyComparable;

public abstract class ComparableNumber extends Number implements MyComparable<Number>
{
	@Override
	public int compareTo(Number number)
	{
		float current = this.floatValue();
		float other = number.floatValue();
		return (int) (current - other);
	}

	@Override
	public abstract double doubleValue();

	@Override
	public abstract float floatValue();

	@Override
	public abstract int intValue();

	@Override
	public abstract long longValue();
}
