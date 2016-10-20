package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;

public class Compass extends Peripheral
{
	Compass(Input input)
	{
		super(input);
	}

	public float azimuth()
	{
		return input().getAzimuth();
	}

	@Override
	public boolean exists()
	{
		return input().isPeripheralAvailable(Input.Peripheral.Compass);
	}

	public float pitch()
	{
		return input().getPitch();
	}

	public float roll()
	{
		return input().getRoll();
	}

	public Vector3 values()
	{
		return new Vector3(azimuth(), pitch(), roll());
	}
}
