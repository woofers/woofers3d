package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;
import com.jaxson.lib.util.Printer;

public class MouseButton extends Input.Buttons implements Button
{
	public static final int[] BUTTONS = {
			LEFT,
			RIGHT,
			MIDDLE,
			BACK,
			FORWARD
	};

	public static final int[] PRIMARY = {
			LEFT,
			RIGHT,
			MIDDLE
	};

	private int button;
	private Input input;

	MouseButton(int button, Input input)
	{
		this.button = button;
		if (!isValid()) throw new InvalidKeyException(button);
	}

	public int button()
	{
		return button;
	}

	private Input input()
	{
		return input;
	}

	@Override
	public boolean isPressed()
	{
		return input().isButtonPressed(button());
	}

	private boolean isValid()
	{
		return LEFT <= button() && button() <= FORWARD;
	}

	public String name()
	{
		switch (button())
		{
			case LEFT:
				return "Left";
			case RIGHT:
				return "Right";
			case MIDDLE:
				return "Scrollwheel";
			case BACK:
				return "Back";
			case FORWARD:
				return "Forward";
		}
		return new Integer(button).toString();
	}

	@Override
	public String toString()
	{
		return new Printer(getClass(),
				new Printer.Label("Name", name()),
				new Printer.Label("Button", button())).toString();
	}
}
