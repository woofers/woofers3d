package com.jaxson.lib.util;

import java.util.NoSuchElementException;

public final class Optional<T>
{
	public static final Optional<?> EMPTY = new Optional<>();

	private static final String NO_VALUE_PRESENT = "No value present";
	private static final String OPTIONAL_TYPE = "Optional[%s]";

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
		Object end = object;
		if (end instanceof Optional<?>)
		{
			Optional<?> other = (Optional<?>) end;
			end = other.get();
		}
		if (isPresent()) return get().equals(end);
		return end == null;
	}

	public T get()
	{
		if (!isPresent())
			throw new NoSuchElementException(NO_VALUE_PRESENT);
		return value;
	}

	public boolean isPresent()
	{
		return value != null;
	}

	public T orElse(T other)
	{
		return isPresent() ? get() : other;
	}

	@Override
	public String toString()
	{
		String object = "Empty";
		if (isPresent()) object = get().toString();
		return String.format(OPTIONAL_TYPE, object);
	}
}
