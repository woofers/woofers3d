package com.jaxson.lib.gdx.backend.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class SpriteRenderer extends Renderer<Sprite>
{
	private Stage stage;

	public SpriteRenderer(Viewport viewport)
	{
		this(new Stage(viewport));
	}

	public SpriteRenderer(Stage stage)
	{
		super();
		this.stage = stage;
	}

	@Override
	public void add(Sprite sprite)
	{
		super.add(sprite);
		getStage().addActor(sprite.toActor());
	}

	public Stage getStage()
	{
		return stage;
	}

	@Override
	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, Viewport viewport)
	{
		if (isEmpty()) return;
		getStage().getViewport().apply();
	    getStage().draw();
	}

	@Override
	public void update(float dt)
	{
	    getStage().act(dt);
	}

	public void resize(int width, int height)
	{
	    getStage().getViewport().update(width, height);
	}
}
