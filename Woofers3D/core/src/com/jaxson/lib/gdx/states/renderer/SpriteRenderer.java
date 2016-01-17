package com.jaxson.lib.gdx.states.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.graphics.g2d.GdxSprite;

public class SpriteRenderer extends Renderer<GdxSprite>
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
		for (GdxSprite sprite: objects)
		{
			sprite.draw(spriteBatch);
		}
		spriteBatch.end();
	}
}
