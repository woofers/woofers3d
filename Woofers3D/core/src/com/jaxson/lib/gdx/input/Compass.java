package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;

public class Compass extends Peripheral
{
	Compass(Input input)
	{
		super(input);
	}

	@Override
	public boolean exists()
	{
		return input().isPeripheralAvailable(Input.Peripheral.Compass);
	}
}
