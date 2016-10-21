package com.jaxson.lib.gdx.backend;

import com.jaxson.lib.util.MyComparable;
import com.jaxson.lib.util.Printer;

public class DisplayMode
{
	private static class ZeroDisplay extends com.badlogic.gdx.Graphics.DisplayMode
	{
		public ZeroDisplay()
		{
			super(0, 0, 0, 0);
		}
	}

	private static final int BPP = 32;
	private static final int REFRESH_RATE = 60;

	public static final DisplayMode WORST = new DisplayMode(0, 0, 0, 0);
	public static final DisplayMode BEST = new DisplayMode(Integer.MAX_VALUE,
			Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

	private int width;
	private int height;
	private int refreshRate;
	private int bitsPerPixel;

	public DisplayMode(com.badlogic.gdx.Graphics.DisplayMode displayMode)
	{
		this(displayMode.width, displayMode.height,
				displayMode.refreshRate, displayMode.bitsPerPixel);
	}

	public DisplayMode(int width, int height)
	{
		this(width, height, REFRESH_RATE);
	}

	public DisplayMode(int width, int height, int refreshRate)
	{
		this(width, height, refreshRate, BPP);
	}

	public DisplayMode(int width, int height, int refreshRate, int bitsPerPixel)
	{
		this.width = width;
		this.height = height;
		this.refreshRate = refreshRate;
		this.bitsPerPixel = bitsPerPixel;
	}

	public int refreshRate()
	{
		return refreshRate;
	}

	public int bitsPerPixel()
	{
		return bitsPerPixel;
	}

	public int area()
	{
		return width() * height();
	}

	public int width()
	{
		return width;
	}

	public int height()
	{
		return height;
	}

	public float aspectRatio()
	{
		return (float)width() / (float)height();
	}

	public com.badlogic.gdx.Graphics.DisplayMode toBestDisplayMode(
			com.badlogic.gdx.Graphics.DisplayMode[] displayModes)
	{
		com.badlogic.gdx.Graphics.DisplayMode bestMode = new ZeroDisplay();
		com.badlogic.gdx.Graphics.DisplayMode worstMode = displayModes[0];
		for (com.badlogic.gdx.Graphics.DisplayMode mode: displayModes)
		{
			if (mode.height >= bestMode.height && mode.height <= height())
			{
				if (mode.width >= bestMode.width && mode.width <= width())
				{
					if (mode.width == bestMode.width
							&& mode.height == bestMode.height)
					{
						if (mode.refreshRate > bestMode.refreshRate
								&& mode.refreshRate <= refreshRate())
						{
							bestMode = mode;
						}
					}
					else if (mode.refreshRate <= refreshRate())
					{
						bestMode = mode;
					}
				}
			}

			if (worstMode.width > mode.width) worstMode = mode;
		}
		return bestMode.width != 0 ? bestMode : worstMode;
	}

	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof DisplayMode)) return false;
		DisplayMode display = (DisplayMode) other;
		return width() == display.width() && height() == display.height();
	}

	@Override
	public String toString()
	{
		return new Printer(getClass(),
				new Printer.Label("Width", width()),
				new Printer.Label("Height", height()),
				new Printer.Label("Refresh Rate", refreshRate()),
				new Printer.Label("Bits Per Pixel", bitsPerPixel()),
				new Printer.Label("Aspect Ratio", aspectRatio())).toString();
	}
}
