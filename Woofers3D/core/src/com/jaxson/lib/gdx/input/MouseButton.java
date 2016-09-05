package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;

public class MouseButton implements Button
{
	private int button;
	private Input input;

	MouseButton(int button, Input input)
	{
		this.button = button;
	}

	public int getButton()
	{
		return button;
	}

	@Override
	public boolean isPressed()
	{
		return getInput().isButtonPressed(getButton());
	}

	private Input getInput()
	{
		return input;
	}
}
