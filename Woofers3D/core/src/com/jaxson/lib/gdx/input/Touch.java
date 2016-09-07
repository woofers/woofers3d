package com.jaxson.lib.gdx.input;

public class Touch implements Key
{
	private boolean touched;
	private boolean wasTouched;
	private int finger;

	Touch(int finger)
	{
		this.finger = finger;
	}

	public int getFinger()
	{
		return finger;
	}

	@Override
	public boolean isDown()
	{
		return touched;
	}

	@Override
	public boolean isPressed()
	{
		return !wasTouched && isDown();
	}

	@Override
	public boolean isReleased()
	{
		return wasTouched && !isDown();
	}

	void setTouched(boolean touched)
	{
		this.touched = touched;
	}

	void transfer()
	{
		this.wasTouched = touched;
	}
}
