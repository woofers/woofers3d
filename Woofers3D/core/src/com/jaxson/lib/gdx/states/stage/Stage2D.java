package com.jaxson.lib.gdx.states.stage;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.gdx.graphics.g2d.Sprite;

public class Stage2D extends Stage<Sprite>
{
	public Stage2D()
	{
		super();
	}

	@Override
	public void dispose()
	{
		for (Sprite sprite: objects)
		{
			sprite.dispose();
		}
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
			spriteBatch.draw(sprite, location.x, location.y);
		}
		spriteBatch.end();
	}

	@Override
	public void update(float dt)
	{
		for (Sprite sprite: objects)
		{
			sprite.update(dt);
		}
	}
}
