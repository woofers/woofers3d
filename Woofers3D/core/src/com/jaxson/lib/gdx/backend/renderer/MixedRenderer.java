package com.jaxson.lib.gdx.backend.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.graphics.g2d.Sprite;
import com.jaxson.lib.gdx.graphics.g3d.entities.types.Entity;
import com.jaxson.lib.gdx.graphics.g3d.environment.MyEnvironment;
import com.jaxson.lib.gdx.util.GameObject;

public class MixedRenderer extends GameObject
{
	private SpriteRenderer spriteRenderer;
	private ModelRenderer modelRenderer;

	public MixedRenderer()
	{
		this.spriteRenderer = new SpriteRenderer();
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
	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, Camera camera)
	{
		spriteRenderer.render(spriteBatch, modelBatch, camera);
		modelRenderer.render(spriteBatch, modelBatch, camera);
	}

	@Override
	public void update(float dt)
	{
		spriteRenderer.update(dt);
		modelRenderer.update(dt);
	}
}
