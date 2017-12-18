package com.jaxson.lib.gdx.graphics.g2d.entities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.gdx.backend.Game;

public class FPSCounter extends Text
{
	private static final String FPS_LABEL = "FPS: ";
	private static final int FONT_PADDING = 20;

	private Game game;

	public FPSCounter(BitmapFont font, Game game)
	{
		super(FPS_LABEL, font);
		this.game = game;
		moveTo(new Vector2(FONT_PADDING, FONT_PADDING));
	}

	public FPSCounter(Game game)
	{
		this(new BitmapFont(), game);
	}

	@Override
	public void update(float dt)
	{
		setText(FPS_LABEL + game.display().fps());
	}
}
