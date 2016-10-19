package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.backend.Display;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.util.Printer;
import com.badlogic.gdx.math.Matrix4;

public class DataAccelerometer extends Peripheral implements Accelerometer
{
	private static final float MIN = -10f;
	private static final float MAX = 10f;
	private static final float RANGE = MAX - MIN;

	private Game game;

	DataAccelerometer(Game game)
	{
		super(game.input());
		this.game = game;
	}

	public Matrix4 rotationMatrix()
	{
		Matrix4 matrix = new Matrix4();
		input().getRotationMatrix(matrix.val);
		return matrix;
	}

	@Override
	public Vector3 alpha()
	{
		return new Vector3(1f, 1f, 1f);
	}

	@Override
	public Vector3 deadZone()
	{
		return Vector3.Zero;
	}

	private Display display()
	{
		return game.display();
	}

	@Override
	public boolean exists()
	{
		return input().isPeripheralAvailable(Input.Peripheral.Accelerometer);
	}

	@Override
	public boolean tiltsBackward()
	{
		return y() < 0f;
	}

	@Override
	public boolean tiltsDown()
	{
		return z() < 0f;
	}

	@Override
	public boolean tiltsForward()
	{
		return y() > 0f;
	}

	@Override
	public boolean tiltsLeft()
	{
		return x() < 0f;
	}

	@Override
	public boolean tiltsRight()
	{
		return x() > 0f;
	}

	@Override
	public boolean tiltsUp()
	{
		return z() > 0f;
	}

	@Override
	public String toString()
	{
		return new Printer(getClass(),
				new Printer.Label("X", x()),
				new Printer.Label("Y", y()),
				new Printer.Label("Z", z())).toString();
	}

	@Override
	public void update(float dt)
	{

	}

	@Override
	public Vector3 values()
	{
		return new Vector3(x(), y(), z());
	}

	@Override
	public float x()
	{
		float x;
		if (display().isLandscape())
		{
			x = input().getAccelerometerY();
			if (display().isReverseLandscape()) x *= -1f;
		}
		else
		{
			x = input().getAccelerometerX();
			if (display().isReversePortrait()) x *= -1f;
		}
		return x / MAX;
	}

	@Override
	public float y()
	{
		float y;
		if (display().isLandscape())
		{
			y = input().getAccelerometerX();
			if (!display().isReverseLandscape()) y *= -1f;

		}
		else
		{
			y = input().getAccelerometerY();
			if (!display().isReversePortrait()) y *= -1f;
		}
		return y / MAX;
	}

	@Override
	public float z()
	{
		return input().getAccelerometerZ() / MAX;
	}
}
