package com.jaxson.lib.gdx.graphics;

public enum DisplayOrientation
{
	Portrait, Landscape, ReversePortrait, ReverseLandscape;

	private static final int PORTRAIT_ANGLE = 0;
	private static final int LANDSCAPE_ANGLE = 90;
	private static final int REVERSE_PORTRAIT_ANGLE = 180;
	private static final int REVERSE_LANDSCAPE_ANGLE = 270;

	public static DisplayOrientation getOrientation(int angle)
	{
		while (angle >= 360)
		{
			angle -= 360;
		}
		while (angle < 0)
		{
			angle += 360;
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

	public int getAngle()
	{
		switch (this)
		{
			case Landscape:
				return LANDSCAPE_ANGLE;
			case ReversePortrait:
				return REVERSE_PORTRAIT_ANGLE;
			case ReverseLandscape:
				return REVERSE_LANDSCAPE_ANGLE;
		}
		return PORTRAIT_ANGLE;
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
