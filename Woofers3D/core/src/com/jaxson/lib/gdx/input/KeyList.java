package com.jaxson.lib.gdx.input;

import com.jaxson.lib.util.MyArrayList;

public class KeyList
{
	private static final String EXCEPTION_MESSAGE = "Key is not valid";

	private MyArrayList<Integer> keycodes;
	private MyArrayList<KeyCombination> keyCombinations;

	public KeyList()
	{
		this((Object) null);
	}

	public KeyList(Object... keys)
	{
		this.keycodes = new MyArrayList<>();
		this.keyCombinations = new MyArrayList<>();
		for (Object key: keys)
		{
			if (key instanceof Integer)
				add((Integer) key);
			else if (key instanceof KeyCombination)
				add((KeyCombination) key);
			else
				throw new IllegalArgumentException(EXCEPTION_MESSAGE);
		}
	}

	public void add(Integer keycode)
	{
		if (!Inputs.isKeyInRange(keycode)) throw new KeyOutOfRangeException();
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
