package com.jaxson.lib.math;

public class Reciprocal extends ComparableNumber
{
	private static final long serialVersionUID = -2798907559979933715L;

	private Number number;

	public Reciprocal(Number number)
	{
		this.number = number;
	}

	public Number denominator()
	{
		return number;
	}

	@Override
	public double doubleValue()
	{
		return 1d / denominator().doubleValue();
	}

	@Override
	public float floatValue()
	{
		return 1f / denominator().floatValue();
	}

	@Override
	public int intValue()
	{
		return 1 / denominator().intValue();
	}

	@Override
	public long longValue()
	{
		return 1L / denominator().longValue();
	}
}
