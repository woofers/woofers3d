package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;
import java.util.Collection;
import java.util.HashMap;

public class TouchScreen implements Peripheral
{
	private static final int MULTITOUCH_SUPPORT = 20;

	private Input input;
	private HashMap<Integer, Touch> touchs;

	TouchScreen(Input input)
	{
		this.input = input;
		this.touchs = new HashMap<>();
		for (int i = 0; i < MULTITOUCH_SUPPORT; i ++)
		{
			touchs.put(i, new Touch(i));
		}
	}

	public boolean areTouched(int amount)
	{
		int touched = 0;
		for (Touch touch: getTouchs())
		{
			if (touch.isDown()) touched ++;
			if (touched >= amount) return true;
		}
		return false;
	}

	public boolean fingersTouched(int amount)
	{
		int touched = 0;
		boolean wasTouched = false;
		for (Touch touch: getTouchs())
		{
			if (touch.isDown()) touched ++;
			if (touch.isPressed()) wasTouched = true;
			if (touched >= amount && wasTouched) return true;
		}
		return false;
	}

	@Override
	public boolean exists()
	{
		return getInput().isPeripheralAvailable(
				Input.Peripheral.MultitouchScreen);
	}

	public int getAmmountOfTouches()
	{
		return getAmmountOfTouches(0, MULTITOUCH_SUPPORT);
	}

	public int getAmmountOfTouches(int min, int max)
	{
		if (min < 0 || MULTITOUCH_SUPPORT < max)
			throw new IllegalArgumentException("Pointers out of range");
		int touches = 0;
		for (int i = min; i < max; i ++)
		{
			if (getTouch(i).isDown()) touches ++;
		}
		return touches;
	}

	public Touch getTouch(int finger)
	{
		return touchs.get(finger);
	}

	public Collection<Touch> getTouchs()
	{
		return touchs.values();
	}

	public boolean isDown()
	{
		for (Touch touch: getTouchs())
		{
			if (touch.isDown()) return true;
		}
		return false;
	}

	public boolean isDown(int finger)
	{
		return getTouch(finger).isDown();
	}

	public boolean isPressed()
	{
		for (Touch touch: getTouchs())
		{
			if (touch.isPressed()) return true;
		}
		return false;
	}

	public boolean isPressed(int finger)
	{
		return getTouch(finger).isPressed();
	}

	public boolean isReleased()
	{
		for (Touch touch: getTouchs())
		{
			if (touch.isReleased()) return true;
		}
		return false;
	}

	public boolean isReleased(int finger)
	{
		return getTouch(finger).isReleased();
	}

	public boolean isTouched()
	{
		return getInput().isTouched();
	}

	public boolean justTouched()
	{
		return getTouch(0).isPressed();
	}

	public boolean oneFingerTouching()
	{
		return fingersToucing(1);
	}

	public boolean threeFingerTouched()
	{
		return fingersTouched(3);
	}

	public boolean threeFingerTouching()
	{
		return fingersToucing(3);
	}

	public boolean twoFingerTouched()
	{
		return fingersTouched(2);
	}

	public boolean twoFingerTouching()
	{
		return fingersToucing(3);
	}

	public boolean fingersToucing(int amount)
	{
		return getAmmountOfTouches() >= amount;
	}

	void transfer()
	{
		for (Touch touch: getTouchs())
		{
			touch.transfer();
		}
	}

	private Input getInput()
	{
		return input;
	}
}
