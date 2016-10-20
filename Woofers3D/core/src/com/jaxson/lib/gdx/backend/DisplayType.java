package com.jaxson.lib.gdx.backend;

import com.jaxson.lib.util.MyComparable;
import com.jaxson.lib.util.Printer;
import com.badlogic.gdx.Graphics.DisplayMode;

public class DisplayType
{
	private static final int BPP = 32;
	private static final int REFRESH_RATE = 60;

	private int width;
	private int height;
	private int refreshRate;
	private int bitsPerPixel;

	public DisplayType(int width, int height)
	{
		this(width, height, REFRESH_RATE);
	}

	public DisplayType(int width, int height, int refreshRate)
	{
		this(width, height, refreshRate, BPP);
	}

	public DisplayType(int width, int height, int refreshRate, int bitsPerPixel)
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
		return width() / height();
	}

	public DisplayMode toDisplayMode()
	{
		DisplayMode[] displayModes = displayModes();
		DisplayMode bestMode = displayModes[0];
		for (DisplayMode mode: displayModes)
		{
			if (bestMode.width() == mode.width()) bestMode = mode;
		}
		return bestMode;
	}

	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof DisplayType)) return false;
		DisplayType display = (DisplayType) other;
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
