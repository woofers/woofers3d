package com.jaxson.lib.gdx.input;

import com.jaxson.lib.util.MyArrayList;
import java.util.Iterator;

public class KeyCombination implements Key, Iterable<Key>
{
	private MyArrayList<Key> keys;

	public KeyCombination(Key... keys)
	{
		this.keys = new MyArrayList<>(keys);
	}

	@Override
	public boolean isDown()
	{
		for (Key key: keys())
		{
			if (!key.isDown()) return false;
		}
		return true;
	}

	@Override
	public boolean isPressed()
	{
		boolean wasPressed = false;
		for (Key key: keys())
		{
			if (!key.isDown()) return false;
			if (key.isPressed()) wasPressed = true;
		}
		return wasPressed;
	}

	@Override
	public boolean isReleased()
	{
		for (Key key: keys())
		{
			if (!key.isReleased()) return false;
		}
		return true;
	}

	@Override
	public Iterator<Key> iterator()
	{
		return keys().iterator();
	}

	private MyArrayList<Key> keys()
	{
		return keys;
	}
}
