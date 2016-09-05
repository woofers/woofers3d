package com.jaxson.lib.gdx.graphics.g2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class FPSCounter extends Text
{
	private static final String FPS = "FPS: ";
	private static final int FONT_PADDING = 20;

	public FPSCounter()
	{
		this(new BitmapFont());
	}

	public FPSCounter(BitmapFont font)
	{
		super(FPS, font);
		setLocation(FONT_PADDING, FONT_PADDING);
	}

	@Override
	public void update(float dt)
	{
		setText(FPS + Gdx.graphics.getFramesPerSecond());
	}
}
