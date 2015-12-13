package com.jaxson.lib.gdx.states.renderables;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.gdx.graphics.g2d.Sprite;

public class SpriteRenderable extends Renderable<Sprite>
{
	public SpriteRenderable()
	{
		super();
	}

	public void render(SpriteBatch spriteBatch)
	{
		if (spriteBatch == null) return;
		if (isEmpty()) return;
		Vector2 location;
		spriteBatch.begin();
		for (Sprite sprite: objects)
		{
			location = sprite.getLocation();
			spriteBatch.draw(sprite.getTexture(), location.x, location.y);
		}
		spriteBatch.end();
	}
}
