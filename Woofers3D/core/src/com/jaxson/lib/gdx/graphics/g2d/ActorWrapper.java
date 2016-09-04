package com.jaxson.lib.gdx.graphics.g2d;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorWrapper extends Actor
{
	private Actorable actorable;

	public ActorWrapper(Actorable actorable)
	{
		this.actorable = actorable;
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		if (!(batch instanceof SpriteBatch)) throw new IllegalArgumentException("Batch is not a spriteBatch");
		actorable.draw((SpriteBatch) batch, parentAlpha);
	}
}
