package com.jaxson.woofers3d.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.backend.GameManager;
import com.jaxson.lib.gdx.backend.State;
import com.jaxson.lib.gdx.graphics.g2d.GdxSprite;
import com.jaxson.lib.gdx.graphics.g3d.Box;

public class PauseState extends State
{
	private static final String PAUSE_SCREEN_PATH = "pauseScreen.png";
	private static final float PAUSE_ALPHA = 3f / 4f;

	private GdxSprite image;
	private Box box;

	public PauseState(GameManager gameManager)
	{
		super(gameManager);

		image = new GdxSprite(PAUSE_SCREEN_PATH);
		image.setAlpha(PAUSE_ALPHA);
		add(image);
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	@Override
	protected void input()
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
