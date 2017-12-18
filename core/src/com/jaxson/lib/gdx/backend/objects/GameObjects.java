package com.jaxson.lib.gdx.backend.objects;

import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.util.GameObject;

public interface GameObjects<T extends GameObject>
{
	public void add(T object);

	public void dispose();

	public boolean isEmpty();

	public void pause();

	public void remove(T object);

	public void render(View view);

	public void resize(int width, int height);

	public void resume();

	public void update(float dt);
}
