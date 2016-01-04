package com.jaxson.lib.gdx.input;

import com.jaxson.lib.util.MyArrayList;

public class KeyList
{
	private MyArrayList<Integer> keys;
	private MyArrayList<KeyCombination> keyCombinations;

	public KeyList()
	{
		this(null);
	}

	public KeyList(Object[] keys)
	{
		this.keys = new MyArrayList<Integer>();
		this.keyCombinations = new MyArrayList<KeyCombination>();
		if (keys == null) return;
		for (Object key: keys)
		{
			if (key instanceof Integer) keys.add((Integer)key);
			if (key instanceof KeyCombination) keyCombinations.add((KeyCombination)key);
		}
	}

	public int[] getKeys()
	{
		return (int[])keys.toArray();
	}

	public KeyCombination[] getKeyCombinations()
	{
		return keyCombinations.toArray();
	}
}
