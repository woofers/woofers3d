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

	private boolean keycodeIsValid()
	{
		return Keys.MIN <= getKeycode() && getKeycode() < Keys.MAX;
	}
}
