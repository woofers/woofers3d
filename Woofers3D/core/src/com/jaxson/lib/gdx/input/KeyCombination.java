package com.jaxson.lib.gdx.input;

import com.jaxson.lib.util.MyArrayList;
import java.util.Iterator;

public class KeyCombination implements Key, Iterable<Key>
{
	private MyArrayList<Key> keys;

	public KeyCombination()
	{
		this((Key) null);
	}

	public KeyCombination(Key... keys)
	{
		this.keys = new MyArrayList<>(keys);
	}

	@Override
	public boolean isDown()
	{
		for (Key key: getKeys())
		{
			if (!key.isDown()) return false;
		}
		return true;
	}

	@Override
	public boolean isPressed()
	{
		boolean wasPressed = false;
		for (Key key: getKeys())
		{
			if (!key.isDown()) return false;
			if (key.isPressed()) wasPressed = true;
		}
		return wasPressed;
	}

	@Override
	public boolean isReleased()
	{
		for (Key key: getKeys())
		{
			if (!key.isReleased()) return false;
		}
		return true;
	}

	@Override
	public Iterator<Key> iterator()
	{
		return getKeys().iterator();
	}

	private MyArrayList<Key> getKeys()
	{
		return keys;
	}
}
