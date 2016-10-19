package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;

public class Gyroscope extends Peripheral
{
	Gyroscope(Input input)
	{
		super(input);
	}

	@Override
	public boolean exists()
	{
		return input().isPeripheralAvailable(Input.Peripheral.Gyroscope);
	}

	public float x()
	{
		return input().getGyroscopeX();
	}

	public float y()
	{
		return input().getGyroscopeY();
	}

	public float z()
	{
		return input().getGyroscopeZ();
	}

	public Vector3 values()
	{
		return new Vector3(x(), y(), z());
	}
}
