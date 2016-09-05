package com.jaxson.lib.gdx.input;

public class Touch
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

	public boolean isDown()
	{
		return touched;
	}

	public boolean isPressed()
	{
		return !wasTouched && isDown();
	}

	public boolean isReleased()
	{
		return wasTouched && !isDown();
	}

	void setTouched(boolean touched)
	{
		this.touched = touched;
	}

	void setWasTouched(boolean wasTouched)
	{
		this.wasTouched = wasTouched;
	}

	void transfer()
	{
		this.wasTouched = touched;
	}
}
