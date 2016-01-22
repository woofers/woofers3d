package com.jaxson.lib.gdx.util;

public class ExceptionBuilder
{
	private static final String CANNOT_BE_NULL = " cannot be null";
	private static final String MUST_BE_POSITIVE = " must be positive";

	public static IllegalArgumentException cannotBeNull(String var)
	{
		return new IllegalArgumentException(var + CANNOT_BE_NULL);
	}

	public static IllegalArgumentException mustBePostive(String var)
	{
		return new IllegalArgumentException(var + MUST_BE_POSITIVE);
	}

}
