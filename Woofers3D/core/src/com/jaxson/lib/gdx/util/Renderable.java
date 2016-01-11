package com.jaxson.lib.gdx.util;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

public interface Renderable
{
	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, Camera camera);

	public void update(float dt);
}
