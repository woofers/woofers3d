package com.jaxson.woofers3d.states;

import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.gdx.graphics.g2d.SpriteActor;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.states.SubState;

public class PauseState extends SubState
{
	private static final String PAUSE_SCREEN_PATH = "pausescreen.png";
	private static final float PAUSE_ALPHA = 3f / 4f;
	private static final float RES_SCALE = 720f / 1080f;

	private SpriteActor image;

	public PauseState(Game game)
	{
		super(game);

		//image = new SpriteActor(PAUSE_SCREEN_PATH);
		//image.setAlpha(PAUSE_ALPHA);
		//image.setY(game().display().center().y
		//		 - image.height() / 2);
		//image.setScale(RES_SCALE);
		//add(image);
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	public void render(View view)
	{
		super.render(view);
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}

	@Override
	protected void input(float dt)
	{

	}
}
