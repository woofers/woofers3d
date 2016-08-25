package com.jaxson.lib.gdx.graphics;

public enum DisplayOrientation
{
	Portrait(0), Landscape(90), ReversePortrait(180), ReverseLandscape(270);

	private static final int PORTRAIT_ANGLE = 0;
	private static final int LANDSCAPE_ANGLE = 90;
	private static final int REVERSE_PORTRAIT_ANGLE = 180;
	private static final int REVERSE_LANDSCAPE_ANGLE = 270;
	private static final int MAX_ANGLE = 360;

	public static DisplayOrientation getOrientation(int angle)
	{
		while (angle >= MAX_ANGLE)
		{
			angle -= MAX_ANGLE;
		}
		while (angle < 0)
		{
			angle += MAX_ANGLE;
		}
		switch (angle)
		{
			case LANDSCAPE_ANGLE:
				return Landscape;
			case REVERSE_PORTRAIT_ANGLE:
				return ReversePortrait;
			case REVERSE_LANDSCAPE_ANGLE:
				return ReverseLandscape;
		}
		return Portrait;
	}

	private int angle;

	private DisplayOrientation(int angle)
	{
		this.angle = angle;
	}

	public int getAngle()
	{
		return angle;
	}

	public boolean isLandscape()
	{
		return this == Landscape || this == ReverseLandscape;
	}

	public boolean isPortrait()
	{
		return this == Portrait || this == ReversePortrait;
	}
}
