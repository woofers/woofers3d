package com.jaxson.woofers3d.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.gdx.graphics.g2d.Sprite;
import com.jaxson.lib.gdx.graphics.g3d.Box;
import com.jaxson.lib.gdx.states.SubState;

public class PauseState extends SubState
{
	private static final String PAUSE_SCREEN_PATH = "pauseScreen.png";
	private static final float PAUSE_ALPHA = 3f / 4f;

	private Sprite image;

	public PauseState(Game gameManager)
	{
		super(gameManager);

		image = new Sprite(PAUSE_SCREEN_PATH);
		image.setAlpha(PAUSE_ALPHA);
		add(image);
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	protected void input(float dt)
	{

	}

	@Override
	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch)
	{
		super.render(spriteBatch, modelBatch);
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
	}
}
