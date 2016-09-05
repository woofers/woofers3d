package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;

public class TouchKeyboard implements Peripheral
{
	private Input input;

	TouchKeyboard(Input input)
	{
		this.input = input;
	}

	@Override
	public boolean exists()
	{
		return getInput().isPeripheralAvailable(Input.Peripheral.OnscreenKeyboard);
	}

	private Input getInput()
	{
		return input;
	}
}
