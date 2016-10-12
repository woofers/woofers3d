package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Keyboard extends Peripheral implements Iterable<KeyboardKey>
{
	private HashMap<Integer, KeyboardKey> keycodeKeys;
	private HashMap<String, KeyboardKey> stringKeys;
	private TouchKeyboard touchKeyboard;

	Keyboard(Input input)
	{
		super(input);
		this.touchKeyboard = new TouchKeyboard(input());
		this.keycodeKeys = new HashMap<>();
		this.stringKeys = new HashMap<>();
		for (int keycode = Keys.MIN; keycode < Keys.MAX; keycode ++)
		{
			KeyboardKey key = new KeyboardKey(keycode);
			String name = key.name();
			keycodeKeys.put(key.keycode(), key);
			stringKeys.put(name.toLowerCase(), key);
		}
	}

	@Override
	public boolean exists()
	{
		return input().isPeripheralAvailable(
				Input.Peripheral.HardwareKeyboard);
	}

	public KeyboardKey key(int keycode)
	{
		KeyboardKey key = keycodeKeys.get(keycode);
		if (key == null) throw new InvalidKeyException(keycode);
		return key;
	}

	public KeyboardKey key(String name)
	{
		KeyboardKey key = stringKeys.get(name.toLowerCase().trim());
		if (key == null) throw new InvalidKeyException(name);
		return key;
	}

	public TouchKeyboard touchKeyboard()
	{
		return touchKeyboard;
	}

	@Override
	public Iterator<KeyboardKey> iterator()
	{
		return keys().iterator();
	}

	void reset()
	{
		for (KeyboardKey key: keys())
		{
			key.setDown(false);
			key.setWasDown(false);
		}
	}

	void transfer()
	{
		for (KeyboardKey key: keys())
		{
			key.transfer();
		}
	}

	private Collection<KeyboardKey> keys()
	{
		return keycodeKeys.values();
	}
}
