package com.jaxson.lib.gdx.states.renderer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.gdx.graphics.g2d.GdxSprite;

public class SpriteRenderer extends Renderer<GdxSprite>
{
	public SpriteRenderer()
	{
		super();
	}

	public void render(SpriteBatch spriteBatch)
	{
		if (spriteBatch == null) return;
		if (isEmpty()) return;
		Vector2 location;
		spriteBatch.begin();
		for (GdxSprite sprite: objects)
		{
			sprite.draw(spriteBatch);
		}
		spriteBatch.end();
	}
}
