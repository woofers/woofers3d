package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.backend.Display;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.math.MyMath;

public class Accelerometer implements Peripheral
{
	private static final float ACCELEROMETER_FORWARD_SCALE = 70f / 100f;
	private static final float ACCELEROMETER_BACK_SCALE = 20f / 100f;
	private static final float ACCELEROMETER_NULL_SCALE =
			100f - ACCELEROMETER_FORWARD_SCALE - ACCELEROMETER_BACK_SCALE;
	private static final float ACCELEROMETER_MAX = 10f;
	private static final float ACCELEROMETER_MIN = -ACCELEROMETER_MAX;
	private static final float ACCELEROMETER_RANGE =
			ACCELEROMETER_MAX - ACCELEROMETER_MIN;

	private Game game;

	Accelerometer(Game game)
	{
		this.game = game;
	}

	@Override
	public boolean exists()
	{
		return getInput().isPeripheralAvailable(Input.Peripheral.Accelerometer);
	}

	public float getBack()
	{
		float value = 0;
		float y = getY();
		if (y > getNullMaxY()) value =
				MyMath.abs((y - getNullMaxY()) / getMinRangeY());
		return MyMath.min(value, 1f);
	}

	public float getBackRangeY()
	{
		return ACCELEROMETER_MAX - getNullMaxY();
	}

	public float getForward()
	{
		float value = 0;
		float y = getY();
		if (y < getNullMinY()) value =
				MyMath.abs((y - getNullMinY()) / getMinRangeY());
		return MyMath.min(value, 1f);
	}

	public float getForwardRangeY()
	{
		return getNullMinY() - ACCELEROMETER_MIN;
	}

	public float getMinRangeY()
	{
		float back = getBackRangeY();
		float forward = getForwardRangeY();
		if (back > forward) return forward;
		return back;
	}

	public float getNullMaxY()
	{
		return ACCELEROMETER_MAX
				- getScaledAccelerometerRange(ACCELEROMETER_BACK_SCALE);
	}

	public float getNullMinY()
	{
		return ACCELEROMETER_MIN
				+ getScaledAccelerometerRange(ACCELEROMETER_FORWARD_SCALE);
	}

	public Vector3 getValues()
	{
		return new Vector3(getX(), getY(), getZ());
	}

	public float getX()
	{
		float x;
		if (getDisplay().isLandscape())
		{
			x = getAbsouluteY();
			if (getDisplay().isReverseLandscape()) x *= -1f;

		}
		else
		{
			x = getAbsouluteX();
			if (getDisplay().isReversePortrait()) x *= -1f;
		}
		return x;
	}

	public float getY()
	{
		float y;
		if (getDisplay().isLandscape())
		{
			y = getAbsouluteX();
			if (getDisplay().isReverseLandscape()) y *= -1f;

		}
		else
		{
			y = getAbsouluteY();
			if (getDisplay().isReversePortrait()) y *= -1f;
		}
		return y;
	}

	public float getZ()
	{
		return getAbsouluteZ();
	}

	public boolean tiltsBackward()
	{
		return getBack() != 0;
	}

	public boolean tiltsForward()
	{
		return getForward() != 0;
	}

	private Vector3 getAbsouluteValues()
	{
		return new Vector3(getAbsouluteX(), getAbsouluteY(), getAbsouluteZ());
	}

	private float getAbsouluteX()
	{
		return getInput().getAccelerometerX();
	}

	private float getAbsouluteY()
	{
		return getInput().getAccelerometerY();
	}

	private float getAbsouluteZ()
	{
		return getInput().getAccelerometerZ();
	}

	private Display getDisplay()
	{
		return game.getDisplay();
	}

	private Input getInput()
	{
		return game.getInput();
	}

	private static float getScaledAccelerometerRange(float scale)
	{
		return scale * ACCELEROMETER_RANGE;
	}
}
