package com.jaxson.lib.gdx.input;

public class KeyboardKey implements Key
{
	public static final int MIN = -1;
	public static final int MAX = 256;

	private int keycode;
	private String name;
	private boolean down;
	private boolean wasDown;

	KeyboardKey(int keycode, String name)
	{
		this.keycode = keycode;
		this.name = name;
	}

	public int getKeycode()
	{
		return keycode;
	}

	public String getName()
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
		return "Key: " + getName() + ", Keycode: " + getKeycode();
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

	private static boolean isKeyInRange(int keycode)
	{
		return MIN <= keycode && keycode < MAX;
	}
}
