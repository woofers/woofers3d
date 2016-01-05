package com.jaxson.lib.gdx.input;

public class KeyCombination
{
	private int[] keys;

	public KeyCombination(int key1, int key2)
	{
		this(new int[] { key1, key2 });
	}

	public KeyCombination(int key1, int key2, int key3)
	{
		this(new int[] { key1, key2, key3 });
	}

	public KeyCombination(int[] keys)
	{
		this.keys = keys;
	}

	public int[] getKeys()
	{
		return keys;
	}
}
