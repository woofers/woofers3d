package com.jaxson.lib.gdx.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Orientation;
import com.jaxson.lib.gdx.graphics.DisplayOrientation;

public class Screen
{
	private static final int NATIVE_ROTATION_OFFSET = 90;

	private Input input;

	Screen(Input input)
	{
		this.input = input;
	}

	public Orientation getNativeOrientation()
	{
		return getInput().getNativeOrientation();
	}

	public int getNativeRotation()
	{
		return getInput().getRotation();
	}

	public DisplayOrientation getOrientation()
	{
		return DisplayOrientation.getOrientation(getRotation());
	}

	public int getRotation()
	{
		int rotation = getNativeRotation();
		if (getNativeOrientation() == Orientation.Portrait) return rotation;
		return rotation + NATIVE_ROTATION_OFFSET;
	}

	public boolean isLandscape()
	{
		return getOrientation().isLandscape();
	}

	public boolean isPortrait()
	{
		return getOrientation().isPortrait();
	}

	public boolean isReverseLandscape()
	{
		return getOrientation() == DisplayOrientation.ReverseLandscape;
	}

	public boolean isReversePortrait()
	{
		return getOrientation() == DisplayOrientation.ReversePortrait;
	}

	public boolean isStandardLandscape()
	{
		return getOrientation() == DisplayOrientation.Landscape;
	}

	public boolean isStandardPortrait()
	{
		return getOrientation() == DisplayOrientation.Portrait;
	}

	private Input getInput()
	{
		return input;
	}
}
