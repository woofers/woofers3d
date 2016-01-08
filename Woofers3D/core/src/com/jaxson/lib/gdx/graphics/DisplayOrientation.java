package com.jaxson.lib.gdx.graphics;

public enum DisplayOrientation
{
	Portrait, Landscape, ReversePortrait, ReverseLandscape;

	public int getAngle()
	{
		switch (this)
		{
			case Landscape:
				return 90;
			case ReversePortrait:
				return 180;
			case ReverseLandscape:
				return 270;
		}
		return 0;
	}

	public boolean isLandscape()
	{
		return this == Landscape || this == ReverseLandscape;
	}

	public boolean isPortrait()
	{
		return this == Portrait || this == ReversePortrait;
	}

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
			case 90:
				return Landscape;
			case 180:
				return ReversePortrait;
			case 270:
				return ReverseLandscape;
		}
		return Portrait;
	}
}
