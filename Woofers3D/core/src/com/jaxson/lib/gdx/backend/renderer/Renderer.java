package com.jaxson.lib.gdx.backend.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.jaxson.lib.gdx.util.GameObject;
import com.jaxson.lib.util.MyArrayList;
import com.jaxson.lib.util.exceptions.NullValueException;
import com.jaxson.lib.gdx.graphics.views.View;

public interface Renderer<T extends GameObject>
{
	public void add(T object);

	public void dispose();

	public boolean isEmpty();

	public void render(View view);

	public void remove(T object);

	public void update(float dt);
}
