package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;
import java.util.Collection;
import java.util.HashMap;

public class Keyboard implements Peripheral
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
			KeyboardKey key = new KeyboardKey(i, Keys.toString(i));
			String name = key.getName();
			keycodeKeys.put(key.getKeycode(), key);
			if (name != null) stringKeys.put(name.toLowerCase(), key);
		}
		for (Key key: getKeys())
			System.out.println(key);
	}

	@Override
	public boolean exists()
	{
		return getInput().isPeripheralAvailable(Input.Peripheral.HardwareKeyboard);
	}

	public KeyboardKey getKey(int keycode)
	{
		return keycodeKeys.get(keycode);
	}

	public KeyboardKey getKey(String name)
	{
		KeyboardKey key = stringKeys.get(name.toLowerCase().trim());
		if (key == null) throw new InvalidKeyException(name);
		return key;
	}

	public Collection<KeyboardKey> getKeys()
	{
		return keycodeKeys.values();
	}

	public TouchKeyboard getTouchKeyboard()
	{
		return touchKeyboard;
	}

	private Input getInput()
	{
		return input;
	}
}
