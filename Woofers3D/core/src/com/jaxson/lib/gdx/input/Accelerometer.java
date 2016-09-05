package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.math.MyMath;

public class Accelerometer implements Peripheral
{
	private static final float ACCELEROMETER_FORWARD_SCALE = 70f / 100f;
	private static final float ACCELEROMETER_BACK_SCALE = 20f / 100f;
	private static final float ACCELEROMETER_NULL_SCALE = 100f - ACCELEROMETER_FORWARD_SCALE - ACCELEROMETER_BACK_SCALE;
	private static final float ACCELEROMETER_MAX = 10f;
	private static final float ACCELEROMETER_MIN = -ACCELEROMETER_MAX;
	private static final float ACCELEROMETER_RANGE = ACCELEROMETER_MAX - ACCELEROMETER_MIN;

	private Input input;
	private Screen screen;

	Accelerometer(Input input, Screen screen)
	{
		this.input = input;
		this.screen = screen;
	}

	@Override
	public boolean exists()
	{
		return getInput().isPeripheralAvailable(Input.Peripheral.Accelerometer);
	}

	public Vector3 getAccelerometer()
	{
		return new Vector3(getAccelerometerX(), getAccelerometerY(), getAccelerometerZ());
	}

	public float getAccelerometerBack()
	{
		float value = 0;
		float y = getAccelerometerY();
		if (y > getAccelerometerNullMaxY()) value = MyMath.abs((y - getAccelerometerNullMaxY()) / getAccelerometerMinRangeY());
		return MyMath.min(value, 1f);
	}

	public float getAccelerometerBackRangeY()
	{
		return ACCELEROMETER_MAX - getAccelerometerNullMaxY();
	}

	public float getAccelerometerForward()
	{
		float value = 0;
		float y = getAccelerometerY();
		if (y < getAccelerometerNullMinY()) value = MyMath.abs((y - getAccelerometerNullMinY()) / getAccelerometerMinRangeY());
		return MyMath.min(value, 1f);
	}

	public float getAccelerometerForwardRangeY()
	{
		return getAccelerometerNullMinY() - ACCELEROMETER_MIN;
	}

	public float getAccelerometerMinRangeY()
	{
		float back = getAccelerometerBackRangeY();
		float forward = getAccelerometerForwardRangeY();
		if (back > forward) return forward;
		return back;
	}

	public float getAccelerometerNullMaxY()
	{
		return ACCELEROMETER_MAX - getScaledAccelerometerRange(ACCELEROMETER_BACK_SCALE);
	}

	public float getAccelerometerNullMinY()
	{
		return ACCELEROMETER_MIN + getScaledAccelerometerRange(ACCELEROMETER_FORWARD_SCALE);
	}

	public float getAccelerometerX()
	{
		float x;
		if (screen.isLandscape())
		{
			x = getAbsouluteAccelerometerY();
			if (screen.isReverseLandscape()) x *= -1f;

		}
		else
		{
			x = getAbsouluteAccelerometerX();
			if (screen.isReversePortrait()) x *= -1f;
		}
		return x;
	}

	public float getAccelerometerY()
	{
		float y;
		if (screen.isLandscape())
		{
			y = getAbsouluteAccelerometerX();
			if (screen.isReverseLandscape()) y *= -1f;

		}
		else
		{
			y = getAbsouluteAccelerometerY();
			if (screen.isReversePortrait()) y *= -1f;
		}
		return y;
	}

	public float getAccelerometerZ()
	{
		return getAbsouluteAccelerometerZ();
	}

	public boolean isAccelerometerBack()
	{
		return getAccelerometerBack() != 0;
	}

	public boolean isAccelerometerForward()
	{
		return getAccelerometerForward() != 0;
	}

	private Vector3 getAbsouluteAccelerometer()
	{
		return new Vector3(getAbsouluteAccelerometerX(), getAbsouluteAccelerometerY(), getAbsouluteAccelerometerZ());
	}

	private float getAbsouluteAccelerometerX()
	{
		return getInput().getAccelerometerX();
	}

	private float getAbsouluteAccelerometerY()
	{
		return getInput().getAccelerometerY();
	}

	private float getAbsouluteAccelerometerZ()
	{
		return getInput().getAccelerometerZ();
	}

	private Input getInput()
	{
		return input;
	}

	private static float getScaledAccelerometerRange(float scale)
	{
		return scale * ACCELEROMETER_RANGE;
	}
}
