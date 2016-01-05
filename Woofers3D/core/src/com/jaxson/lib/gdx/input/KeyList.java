package com.jaxson.lib.gdx.input;

import com.jaxson.lib.util.MyArrayList;

public class KeyList
{
	private MyArrayList<Integer> keycodes;
	private MyArrayList<KeyCombination> keyCombinations;

	public KeyList()
	{
		this(null);
	}

	public KeyList(Object[] keys)
	{
		this.keycodes = new MyArrayList<Integer>();
		this.keyCombinations = new MyArrayList<KeyCombination>();
		for (Object key: keys)
		{
			if (key instanceof Integer) keycodes.add((Integer) key);
			if (key instanceof KeyCombination) keyCombinations.add((KeyCombination) key);
		}
	}

	public KeyCombination[] getKeyCombinations()
	{
		return keyCombinations.toArray(new KeyCombination[0]);
	}

	public int[] getKeys()
	{
		int size = keycodes.size();
		int[] intKeycodes = new int[size];
		for (int i = 0; i < size; i++)
		{
			intKeycodes[i] = keycodes.get(i);
		}
		return intKeycodes;
	}
}
