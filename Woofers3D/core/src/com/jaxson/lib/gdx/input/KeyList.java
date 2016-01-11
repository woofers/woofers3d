package com.jaxson.lib.gdx.input;

import com.jaxson.lib.util.MyArrayList;

public class KeyList
{
	private static final String EXCEPTION_MESSAGE = "Key is not valid";

	private MyArrayList<Integer> keycodes;
	private MyArrayList<KeyCombination> keyCombinations;

	public KeyList()
	{
		this(null);
	}

	public KeyList(Object key1, Object key2)
	{
		this(new Object[]{ key1, key2 });
	}

	public KeyList(Object key1, Object key2, Object key3)
	{
		this(new Object[]{ key1, key2, key3 });
	}

	public KeyList(Object[] keys)
	{
		this.keycodes = new MyArrayList<Integer>();
		this.keyCombinations = new MyArrayList<KeyCombination>();
		for (Object key: keys)
		{
			if (!isValidKey(key)) throw new IllegalArgumentException(EXCEPTION_MESSAGE);
			if (key instanceof Integer) add((Integer) key);
			if (key instanceof KeyCombination) add((KeyCombination) key);
		}
	}

	public void add(Integer keycode)
	{
		keycodes.add(keycode);
	}

	public void add(KeyCombination keyCombination)
	{
		keyCombinations.add(keyCombination);
	}

	public KeyCombination[] getKeyCombinations()
	{
		return keyCombinations.toArray(new KeyCombination[0]);
	}

	public int[] getKeys()
	{
		int size = keycodes.size();
		int[] intKeycodes = new int[size];
		for (int i = 0; i < size; i ++)
		{
			intKeycodes[i] = keycodes.get(i);
		}
		return intKeycodes;
	}

	public boolean isValidKey(Object key)
	{
		return key instanceof Integer || key instanceof KeyCombination;
	}
}
