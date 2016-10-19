package com.jaxson.lib.gdx.graphics;

public class DisplayOrientation
{
	private static final int MAX_ANGLE = 360;
	private static final int FULL_ROTATION = MAX_ANGLE / 4;

	public static final DisplayOrientation PORTRAIT
			= new DisplayOrientation(0);

	public static final DisplayOrientation LANDSCAPE
			= new DisplayOrientation(90);

	public static final DisplayOrientation REVERSE_PORTRAIT
			= new DisplayOrientation(180);

	public static final DisplayOrientation REVERSE_LANDSCAPE
			= new DisplayOrientation(270);

	private int angle;

	public DisplayOrientation(int angle)
	{
		while (angle >= MAX_ANGLE)
		{
			angle -= MAX_ANGLE;
		}
		while (angle < 0)
		{
			angle += MAX_ANGLE;
		}
		this.angle = newAngle(angle, FULL_ROTATION);
	}

	public int angle()
	{
		return angle;
	}

	@Override
	public boolean equals(Object other)
	{
		if (other instanceof DisplayOrientation)
		{
			DisplayOrientation otherOrientation = (DisplayOrientation) other;
			return angle() == otherOrientation.angle();
		}
		if (other instanceof Number)
		{
			Number otherNumber = (Number) other;
			return angle() == otherNumber.intValue();
		}
		return false;
	}

	public boolean isLandscape()
	{
		return equals(LANDSCAPE) || equals(REVERSE_LANDSCAPE);
	}

	public boolean isPortrait()
	{
		return equals(PORTRAIT) || equals(REVERSE_PORTRAIT);
	}

	private static int newAngle(int angle, int multiple)
	{
		return (int) (Math.round((double) angle / multiple) * multiple);
	}
}
