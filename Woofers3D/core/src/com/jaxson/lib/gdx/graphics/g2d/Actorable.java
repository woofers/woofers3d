package com.jaxson.lib.gdx.graphics.g2d;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public interface Actorable
{
	public void draw(SpriteBatch batch, float parentAlpha);

	public Actor toActor();
}
