package com.jaxson.lib.gdx.backend.renderer;

import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.util.GameObject;

public interface Renderer<T extends GameObject>
{
	public void add(T object);

	public void dispose();

	public boolean isEmpty();

	public void remove(T object);

	public void render(View view);

	public void update(float dt);
}
