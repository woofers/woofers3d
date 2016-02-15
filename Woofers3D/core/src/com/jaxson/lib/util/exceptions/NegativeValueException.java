package com.jaxson.lib.util.exceptions;

public class NegativeValueException extends IllegalArgumentException
{
	private static final String MUST_BE_POSITIVE = " must be positive";

	public NegativeValueException()
	{
		super();
	}

	public NegativeValueException(String var)
	{
		super(var + MUST_BE_POSITIVE);
	}
}
