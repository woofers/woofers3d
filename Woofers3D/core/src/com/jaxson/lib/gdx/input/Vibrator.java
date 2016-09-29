package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;

public class Vibrator extends Peripheral
{
	public static final int NO_REPEAT = -1;

	Vibrator(Input input)
	{
		super(input);
	}

	@Override
	public boolean exists()
	{
		return input().isPeripheralAvailable(Input.Peripheral.Vibrator);
	}

	public void stop()
	{
		input().cancelVibrate();
	}

	public void start(int milliseconds)
	{
		input().vibrate(milliseconds);
	}

	public void start(long[] pattern)
	{
		start(pattern, NO_REPEAT);
	}

	public void start(long[] pattern, int loopCount)
	{
		input().vibrate(pattern, loopCount);
	}
}
