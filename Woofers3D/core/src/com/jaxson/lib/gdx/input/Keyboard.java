package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Keyboard implements Peripheral, Iterable<KeyboardKey>
{
	private HashMap<Integer, KeyboardKey> keycodeKeys;
	private HashMap<String, KeyboardKey> stringKeys;
	private Input input;
	private TouchKeyboard touchKeyboard;

	Keyboard(Input input)
	{
		this.input = input;
		this.touchKeyboard = new TouchKeyboard(getInput());
		this.keycodeKeys = new HashMap<>();
		this.stringKeys = new HashMap<>();
		for (int i = Keys.MIN; i < Keys.MAX; i ++)
		{
			KeyboardKey key = new KeyboardKey(i);
			String name = key.getName();
			keycodeKeys.put(key.getKeycode(), key);
			if (name != null) stringKeys.put(name.toLowerCase(), key);
		}
	}

	@Override
	public boolean exists()
	{
		return getInput().isPeripheralAvailable(
				Input.Peripheral.HardwareKeyboard);
	}

	public KeyboardKey getKey(int keycode)
	{
		KeyboardKey key = keycodeKeys.get(keycode);
		if (key == null) throw new InvalidKeyException(keycode);
		return key;
	}

	public KeyboardKey getKey(String name)
	{
		KeyboardKey key = stringKeys.get(name.toLowerCase().trim());
		if (key == null) throw new InvalidKeyException(name);
		return key;
	}

	public TouchKeyboard getTouchKeyboard()
	{
		return touchKeyboard;
	}

	@Override
	public Iterator<KeyboardKey> iterator()
	{
		return getKeys().iterator();
	}

	void reset()
	{
		for (KeyboardKey key: getKeys())
		{
			key.setDown(false);
			key.setWasDown(false);
		}
	}

	void transfer()
	{
		for (KeyboardKey key: getKeys())
		{
			key.transfer();
		}
	}

	private Input getInput()
	{
		return input;
	}

	private Collection<KeyboardKey> getKeys()
	{
		return keycodeKeys.values();
	}
}
