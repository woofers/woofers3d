package com.jaxson.lib.gdx.input;

public class KeyCombination
{
	private static final String EXCEEDED_KEY_ROLLOVER = "Max key roll over exceeded";
	private static final int MAX_KEY_ROLLOVER = 6;

	private int[] keys;

	public KeyCombination(int... keys)
	{
		this.keys = keys;
		if (keys.length > MAX_KEY_ROLLOVER) throw new IllegalArgumentException(EXCEEDED_KEY_ROLLOVER);
		for (int key: keys)
		{
			if (!InputHandler.isKeyInRange(key)) throw new KeyOutOfRangeException();
		}
	}

	public int[] getKeys()
	{
		return keys;
	}
}
