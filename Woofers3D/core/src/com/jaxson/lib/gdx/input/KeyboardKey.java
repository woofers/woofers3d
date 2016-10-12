package com.jaxson.lib.gdx.input;

import com.jaxson.lib.util.Printer;

public class KeyboardKey implements Key
{
	private int keycode;
	private String name;
	private boolean down;
	private boolean wasDown;

	KeyboardKey(int keycode)
	{
		this.keycode = keycode;
		this.name = Keys.toString(keycode);
		if (!isValid()) throw new InvalidKeyException(keycode);
	}

	public int keycode()
	{
		return keycode;
	}

	public String name()
	{
		return name;
	}

	@Override
	public boolean isDown()
	{
		return down;
	}

	@Override
	public boolean isPressed()
	{
		return !wasDown && isDown();
	}

	@Override
	public boolean isReleased()
	{
		return wasDown && !isDown();
	}

	@Override
	public String toString()
	{
		return new Printer(getClass(),
				new Printer.Label("Name", name()),
				new Printer.Label("Keycode", keycode())).toString();
	}

	void setDown(boolean down)
	{
		this.down = down;
	}

	void setWasDown(boolean down)
	{
		this.wasDown = wasDown;
	}

	void transfer()
	{
		this.wasDown = down;
	}

	private boolean isValid()
	{
		return Keys.MIN <= keycode() && keycode() < Keys.MAX;
	}
}
