package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.lib.gdx.backend.Display;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.math.MyMath;
import java.util.HashMap;
import com.jaxson.lib.util.Printer;

public class Accelerometer extends Peripheral
{
	public static final float MAX = 10f;
	public static final float MIN = -MAX;
	public static final float RANGE = MAX - MIN;

	private static final float ALPHA = 0.2f;
	public static final float DEAD_ZONE = 1.75f;

	private Game game;
	private float x;
	private float y;
	private float z;
	private float oldX;
	private float oldY;
	private float oldZ;

	Accelerometer(Game game)
	{
		super(game.input());
		this.game = game;
	}

	@Override
	public boolean exists()
	{
		return input().isPeripheralAvailable(Input.Peripheral.Accelerometer);
	}

	public Vector3 values()
	{
		return new Vector3(x(), y(), z());
	}

	public float absouluteX()
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
		return x;
	}

	public float absouluteY()
	{
		float y;
		if (display().isLandscape())
		{
			y = input().getAccelerometerX();
			if (display().isReverseLandscape()) y *= -1f;

		}
		else
		{
			y = input().getAccelerometerY();
			if (display().isReversePortrait()) y *= -1f;
		}
		return y * -1f;
	}

	public float absouluteZ()
	{
		return input().getAccelerometerZ();
	}

	public Vector3 absouluteValues()
	{
		return new Vector3(absouluteX(), absouluteY(), absouluteZ());
	}

	private Display display()
	{
		return game.display();
	}

	public float z()
	{
		return oldZ + ALPHA * (z - oldZ);
	}

	public float y()
	{
		return oldY + ALPHA * (y - oldY);
	}

	public float x()
	{
		return oldX + ALPHA * (x - oldX);
	}

	public boolean tiltsForward()
	{
		return y() > DEAD_ZONE;
	}

	public boolean tiltsBackward()
	{
		return y() < -DEAD_ZONE;
	}

	public boolean tiltsLeft()
	{
		return x() < -DEAD_ZONE;
	}

	public boolean tiltsRight()
	{
		return x() > DEAD_ZONE;
	}

	public boolean tiltsUp()
	{
		return z() > DEAD_ZONE;
	}

	public boolean tiltsDown()
	{
		return z() < -DEAD_ZONE;
	}

	public void update(float dt)
	{
		if (!exists()) return;
		this.oldX = x;
		this.oldY = y;
		this.oldZ = z;
		this.x = absouluteX();
		this.y = absouluteY();
		this.z = absouluteZ();
	}

	@Override
	public String toString()
	{
		return new Printer(getClass(),
				new Printer.Label("X", x()),
				new Printer.Label("Y", y()),
				new Printer.Label("Z", z())).toString();
	}
}
