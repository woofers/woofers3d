package com.jaxson.lib.util;

import java.util.NoSuchElementException;

public final class Optional<T> implements Uncertainty, Unwrapable<T>
{
	public static final Optional<?> EMPTY = new Optional<>();

	private static final String NO_VALUE_PRESENT = "No value present";
	private static final String NULL_TYPE = "Empty";

	private final T value;

	public Optional()
	{
		this(null);
	}

	public Optional(T value)
	{
		this.value = value;
	}

	@Override
	public boolean equals(Object object)
	{
		if (object instanceof Optional<?>)
		{
			Optional<?> optional = (Optional<?>) object;
			object = optional.unwrap();
		}
		if (exists()) return unwrap().equals(object);
		return object == null;
	}

	@Override
	public boolean exists()
	{
		return value != null;
	}

	public T orElse(T other)
	{
		return exists() ? unwrap() : other;
	}

	@Override
	public String toString()
	{
		return new Printer(getClass(),
				new Printer.Label(value)).toString();
	}

	@Override
	public T unwrap()
	{
		if (!exists())
			throw new NoSuchElementException(NO_VALUE_PRESENT);
		return value;
	}
}
