package com.jaxson.lib.gdx.input;

import java.util.Collection;
import java.util.HashMap;
import com.badlogic.gdx.Input;

public class TouchScreen extends Peripheral
{
	private static final int MULTITOUCH_SUPPORT = 20;

	private HashMap<Integer, Touch> touchs;

	TouchScreen(Input input)
	{
		super(input);
		this.touchs = new HashMap<>();
		for (int i = 0; i < MULTITOUCH_SUPPORT; i ++)
		{
			touchs.put(i, new Touch(i));
		}
	}

	public int ammountOfTouches()
	{
		return ammountOfTouches(0, MULTITOUCH_SUPPORT);
	}

	public int ammountOfTouches(int min, int max)
	{
		if (min < 0 || MULTITOUCH_SUPPORT < max)
			throw new IllegalArgumentException("Pointers out of range");
		int touches = 0;
		for (int i = min; i < max; i ++)
		{
			if (touch(i).isDown()) touches ++;
		}
		return touches;
	}

	public boolean areTouched(int amount)
	{
		int touched = 0;
		for (Touch touch: touchs())
		{
			if (touch.isDown()) touched ++;
			if (touched >= amount) return true;
		}
		return false;
	}

	@Override
	public boolean exists()
	{
		return input().isPeripheralAvailable(
				Input.Peripheral.MultitouchScreen);
	}

	public boolean fingersTouched(int amount)
	{
		int touched = 0;
		boolean wasTouched = false;
		for (Touch touch: touchs())
		{
			if (touch.isDown()) touched ++;
			if (touch.isPressed()) wasTouched = true;
			if (touched >= amount && wasTouched) return true;
		}
		return false;
	}

	public boolean fingersToucing(int amount)
	{
		return ammountOfTouches() >= amount;
	}

	public boolean isDown()
	{
		for (Touch touch: touchs())
		{
			if (touch.isDown()) return true;
		}
		return false;
	}

	public boolean isDown(int finger)
	{
		return touch(finger).isDown();
	}

	public boolean isPressed()
	{
		for (Touch touch: touchs())
		{
			if (touch.isPressed()) return true;
		}
		return false;
	}

	public boolean isPressed(int finger)
	{
		return touch(finger).isPressed();
	}

	public boolean isReleased()
	{
		for (Touch touch: touchs())
		{
			if (touch.isReleased()) return true;
		}
		return false;
	}

	public boolean isReleased(int finger)
	{
		return touch(finger).isReleased();
	}

	public boolean isTouched()
	{
		return input().isTouched();
	}

	public boolean justTouched()
	{
		return touch(0).isPressed();
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

	public Touch touch(int finger)
	{
		return touchs.get(finger);
	}

	public Collection<Touch> touchs()
	{
		return touchs.values();
	}

	void transfer()
	{
		for (Touch touch: touchs())
		{
			touch.transfer();
		}
	}

	public boolean twoFingerTouched()
	{
		return fingersTouched(2);
	}

	public boolean twoFingerTouching()
	{
		return fingersToucing(3);
	}
}
