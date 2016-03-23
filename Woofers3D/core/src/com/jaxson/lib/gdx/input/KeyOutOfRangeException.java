package com.jaxson.lib.gdx.input;

public class KeyOutOfRangeException extends IllegalArgumentException
{
	/**
	 *
	 */
	private static final long serialVersionUID = -7919938276213778602L;
	private static final String OUT_OF_RANGE = " is out of range";

	public KeyOutOfRangeException()
	{
		super();
	}

	public KeyOutOfRangeException(int keycode)
	{
		this(keycode + OUT_OF_RANGE);
	}

	public KeyOutOfRangeException(String message)
	{
		super(message);
	}
}
