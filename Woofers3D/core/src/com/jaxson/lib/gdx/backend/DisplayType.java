package com.jaxson.lib.gdx.backend;

import com.jaxson.lib.util.MyComparable;
import com.jaxson.lib.util.Printer;

public class DisplayType implements MyComparable<DisplayType>
{
	private static class DisplayMode extends com.badlogic.gdx.Graphics.DisplayMode
	{
		private static final int BPP = 32;

		public DisplayMode(int width, int height, int refreshRate, int bitsPerPixel)
		{
			super(width, height, refreshRate, bitsPerPixel);
		}
	}

	public DisplayType(int width, int height)
	{
		//this.width = width;
		//this.height = height;
	}

	@Override
	public int compareTo(DisplayType other)
	{
		return 1;
		//return (width() - other.width()) * (height() - other.height());
	}
/*
	public float area()
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

	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof DisplayType)) return false;
		DisplayType rectangle = (DisplayType) other;
		return width() == rectangle.width() && height() == rectangle.height();
	}

	@Override
	public String toString()
	{
		return new Printer(getClass(),
				new Printer.Label("Width", width()),
				new Printer.Label("Height", height()),
				new Printer.Label("Aspect Ratio", aspectRatio())).toString();
	}
*/
}
