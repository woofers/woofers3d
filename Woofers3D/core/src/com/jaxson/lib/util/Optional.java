package com.jaxson.lib.util;

import java.util.NoSuchElementException;

public final class Optional<T>
{
	private static final Optional<?> EMPTY = new Optional<>();

	private T value;

	public Optional()
	{
		this(null);
	}

	public Optional(T value)
	{
		this.value = value;
	}

	public T get()
	{
		if (!isPresent()) throw new NoSuchElementException("No value present");
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
	public boolean equals(Object object)
	{
		Object origin = get();
		Object end = null;
		if (object instanceof Optional)
		{
			Optional<?> other = (Optional<?>) object;
			end = other.get();
		}
		else
		{
			end = object;
		}
		if (isPresent()) return origin.equals(end);
		return end == null;
	}

	@Override
	public String toString()
	{
		String string = isPresent() ? value.toString() : "Not Present";
		return String.format("Optional[%s]", value);
	}
}