package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;

public class Vibrator implements Peripheral
{
	private Input input;

	Vibrator(Input input)
	{
		this.input = input;
	}

	@Override
	public boolean exists()
	{
		return getInput().isPeripheralAvailable(Input.Peripheral.Vibrator);
	}

	private Input getInput()
	{
		return input;
	}
}
