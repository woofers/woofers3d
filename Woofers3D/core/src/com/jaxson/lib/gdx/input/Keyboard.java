package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;
import java.util.Collection;
import java.util.HashMap;

public class Keyboard implements Peripheral
{
	public static final String ANY_KEY = "Any Key";

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
		for (int i = KeyboardKey.MIN; i < KeyboardKey.MAX; i ++)
		{
			KeyboardKey key = new KeyboardKey(i, keyCodeToString(i));
			String name = key.getName();
			keycodeKeys.put(key.getKeycode(), key);
			if (name != null) stringKeys.put(name.toLowerCase(), key);
		}
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
		return stringKeys.get(name.toLowerCase().trim());
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

	private static String keyCodeToString(int keycode)
	{
		return keycode == -1 ? ANY_KEY : Input.Keys.toString(keycode);
	}
}
