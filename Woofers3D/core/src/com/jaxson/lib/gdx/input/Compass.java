package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;

public class Compass implements Peripheral
{
	private Input input;

	Compass(Input input)
	{
		this.input = input;
	}

	@Override
	public boolean exists()
	{
		return getInput().isPeripheralAvailable(Input.Peripheral.Compass);
	}

	private Input getInput()
	{
		return input;
	}
}
