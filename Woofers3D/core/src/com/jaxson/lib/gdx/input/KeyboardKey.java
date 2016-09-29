package com.jaxson.lib.gdx.input;

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
		if (!keycodeIsValid()) throw new InvalidKeyException(keycode);
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
		return "Key: " + name() + ", Keycode: " + keycode();
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

	private boolean keycodeIsValid()
	{
		return Keys.MIN <= keycode() && keycode() < Keys.MAX;
	}
}
