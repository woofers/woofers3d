package com.jaxson.lib.gdx.states.renderables;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.graphics.g2d.Sprite;
import com.jaxson.lib.gdx.graphics.g3d.Entity;

public class MixedRenderable
{
	private SpriteRenderable spriteRenderable;

	private ModelRenderable modelRenderable;

	public MixedRenderable()
	{
		this.spriteRenderable = new SpriteRenderable();
		this.modelRenderable = new ModelRenderable();
	}

	public void add(Entity model)
	{
		modelRenderable.add(model);
	}

	public void add(Sprite sprite)
	{
		spriteRenderable.add(sprite);
	}

	public void dispose()
	{
		spriteRenderable.dispose();
		modelRenderable.dispose();
	}

	public boolean isEmpty()
	{
		return spriteRenderable.isEmpty() && modelRenderable.isEmpty();
	}

	public void remove(Entity model)
	{
		modelRenderable.remove(model);
	}

	public void remove(Sprite sprite)
	{
		spriteRenderable.add(sprite);
	}

	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, Camera camera)
	{
		spriteRenderable.render(spriteBatch);
		modelRenderable.render(modelBatch, camera);
	}

	public void update(float dt)
	{
		spriteRenderable.update(dt);
		modelRenderable.update(dt);
	}
}
