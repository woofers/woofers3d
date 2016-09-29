package com.jaxson.lib.gdx.input;

import com.jaxson.lib.util.Uncertainty;
import com.badlogic.gdx.Input;

public abstract class Peripheral implements Uncertainty
{
	private Input input;

	public Peripheral(Input input)
	{
		this.input = input;
	}

	protected Input input()
	{
		return input;
	}

	public abstract boolean exists();
}
