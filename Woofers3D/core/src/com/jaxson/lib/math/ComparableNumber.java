package com.jaxson.lib.math;

import com.jaxson.lib.util.MyComparable;

public abstract class ComparableNumber extends Number implements MyComparable<Number>
{
	public int compareTo(Number number)
	{
		float current = this.floatValue();
		float other = number.floatValue();
		if (current > other)
		{
			return MyComparable.GREATHER;
		}
		else if (current < other)
		{
			return MyComparable.LESS;
		}
		return MyComparable.EQUAL;
	}

    public abstract int intValue();

    public abstract long longValue();

    public abstract float floatValue();

    public abstract double doubleValue();
}
