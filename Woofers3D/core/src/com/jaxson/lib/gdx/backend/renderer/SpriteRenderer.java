package com.jaxson.lib.gdx.backend.renderer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.jaxson.lib.gdx.graphics.g2d.Sprite;
import com.jaxson.lib.gdx.graphics.views.View;

public class SpriteRenderer extends Renderer<Sprite>
{
	private Stage stage;

	public SpriteRenderer(Stage stage)
	{
		super();
		this.stage = stage;
	}

	public SpriteRenderer(View view)
	{
		this(new Stage(view.getSpriteView()));
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
	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, View view)
	{
		if (isEmpty()) return;
		view.getSpriteView().apply();
		getStage().draw();
	}

	@Override
	public void resize(int width, int height)
	{
		getStage().getViewport().update(width, height);
	}

	@Override
	public void update(float dt)
	{
		getStage().act(dt);
	}
}
