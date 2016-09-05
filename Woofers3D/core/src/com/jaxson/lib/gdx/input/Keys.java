package com.jaxson.lib.gdx.input;

import com.jaxson.lib.util.MyArrayList;
import java.util.Iterator;

public class Keys implements Key, Iterable<Key>
{
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

	public MyArrayList<Key> getKeys()
	{
		return keys;
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
}
