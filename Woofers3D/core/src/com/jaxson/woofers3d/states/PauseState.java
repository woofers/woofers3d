package com.jaxson.woofers3d.states;

import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.gdx.graphics.g2d.SpriteActor;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.states.SubState;

public class PauseState extends SubState
{
	private static final String PAUSE_SCREEN_PATH = "pauseScreen.png";
	private static final float PAUSE_ALPHA = 3f / 4f;

	private SpriteActor image;

	public PauseState(Game gameManager)
	{
		super(gameManager);

		image = new SpriteActor(PAUSE_SCREEN_PATH);
		image.setAlpha(PAUSE_ALPHA);
		add(image);
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
