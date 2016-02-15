package com.jaxson.lib.gdx.backend.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.graphics.g2d.Sprite;

public class SpriteRenderer extends Renderer<Sprite>
{
	public SpriteRenderer()
	{
		super();
	}

	@Override
	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, Camera camera)
	{
		if (isEmpty()) return;
		checkAgruments(spriteBatch, modelBatch, camera);
		spriteBatch.begin();
		for (Sprite sprite: getObjects())
		{
			sprite.draw(spriteBatch);
		}
		spriteBatch.end();
	}
}
