package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;

public class TouchKeyboard extends Peripheral
{
	TouchKeyboard(Input input)
	{
		super(input);
	}

	@Override
	public boolean exists()
	{
		return input().isPeripheralAvailable(
				Input.Peripheral.OnscreenKeyboard);
	}
}
