package com.jaxson.woofers3d.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.gdx.backend.GameManager;
import com.jaxson.lib.gdx.backend.State;
import com.jaxson.lib.gdx.graphics.g2d.GdxSprite;
import com.jaxson.lib.gdx.graphics.g3d.Box;

public class PauseState extends State
{
	private GdxSprite image;
	private Box box;

	public PauseState(GameManager gameManager)
	{
		super(gameManager);

		image = new GdxSprite("pauseScreen.png");
		image.setLocation(new Vector2(0, 0));
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
