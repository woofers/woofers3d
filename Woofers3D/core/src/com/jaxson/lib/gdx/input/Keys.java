package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;
import com.jaxson.lib.util.MyArrayList;
import java.util.Iterator;

public class Keys extends Input.Keys implements Key, Iterable<Key>
{
	public static final int ANY_KEY = -1;
	private static final String ANY_KEY_NAME = "Any Key";
	public static final int MIN = ANY_KEY;
	public static final int MAX = 256;

	private MyArrayList<Key> keys;

	public Keys()
	{
		this((Key) null);
	}

	public Keys(Key... keys)
	{
		this.keys = new MyArrayList<>(keys);
	}

	public void add(Key... keys)
	{
		getKeys().addAll(keys);
	}

	public void add(Keys... keys)
	{
		for (Keys keyList: keys)
		{
			getKeys().addAll(keyList.getKeys());
		}
	}

	@Override
	public boolean isDown()
	{
		for (Key key: getKeys())
		{
			if (key.isDown()) return true;
		}
		return false;
	}

	@Override
	public boolean isPressed()
	{
		for (Key key: getKeys())
		{
			if (key.isPressed()) return true;
		}
		return false;
	}

	@Override
	public boolean isReleased()
	{
		for (Key key: getKeys())
		{
			if (key.isReleased()) return true;
		}
		return false;
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

	public static String toString(int keycode)
	{
		if (keycode == ANY_KEY) return ANY_KEY_NAME;
		return Input.Keys.toString(keycode);
	}
}
