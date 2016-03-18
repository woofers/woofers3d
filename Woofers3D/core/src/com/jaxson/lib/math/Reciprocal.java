package com.jaxson.lib.math;

public class Reciprocal extends ComparableNumber
{
	private static final long serialVersionUID = -2798907559979933715L;

	private Number number;

	public Reciprocal(Number number)
	{
		this.number = number;
	}

	@Override
	public double doubleValue()
	{
		return 1d / getNumber().doubleValue();
	}

	@Override
	public float floatValue()
	{
		return 1f / getNumber().floatValue();
	}

	public Number getNumber()
	{
		return number;
	}

	@Override
	public int intValue()
	{
		return 1 / getNumber().intValue();
	}

	@Override
	public long longValue()
	{
		return 1l / getNumber().longValue();
	}
}
