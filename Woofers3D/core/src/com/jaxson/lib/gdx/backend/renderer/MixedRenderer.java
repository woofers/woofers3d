package com.jaxson.lib.gdx.backend.renderer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.jaxson.lib.gdx.graphics.g2d.Sprite;
import com.jaxson.lib.gdx.graphics.g3d.entities.types.Entity;
import com.jaxson.lib.gdx.graphics.g3d.environment.MyEnvironment;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.util.GameObject;

public class MixedRenderer extends GameObject
{
	private SpriteRenderer spriteRenderer;
	private ModelRenderer modelRenderer;

	public MixedRenderer(View view)
	{
		this.spriteRenderer = new SpriteRenderer(view);
		this.modelRenderer = new ModelRenderer();
	}

	public void add(Entity model)
	{
		modelRenderer.add(model);
	}

	public void add(Sprite sprite)
	{
		spriteRenderer.add(sprite);
	}

	@Override
	public void dispose()
	{
		spriteRenderer.dispose();
		modelRenderer.dispose();
	}

	public MyEnvironment getEnvironment()
	{
		return modelRenderer.getEnvironment();
	}

	public Stage getStage()
	{
		return spriteRenderer.getStage();
	}

	public boolean isEmpty()
	{
		return spriteRenderer.isEmpty() && modelRenderer.isEmpty();
	}

	public void remove(Entity model)
	{
		modelRenderer.remove(model);
	}

	public void remove(Sprite sprite)
	{
		spriteRenderer.add(sprite);
	}

	@Override
	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, View view)
	{
		spriteRenderer.render(spriteBatch, modelBatch, view);
		modelRenderer.render(spriteBatch, modelBatch, view);
	}

	@Override
	public void resize(int width, int height)
	{
		spriteRenderer.resize(width, height);
		modelRenderer.resize(width, height);
	}

	@Override
	public void update(float dt)
	{
		spriteRenderer.update(dt);
		modelRenderer.update(dt);
	}
}
