package com.jaxson.lib.gdx.backend.objects;

import com.jaxson.lib.gdx.graphics.g2d.Sprite;
import com.jaxson.lib.gdx.graphics.g3d.entities.types.Entity;
import com.jaxson.lib.gdx.graphics.g3d.environment.MyEnvironment;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.util.GameObject;

public class MixedObjects extends GameObject implements GameObjects<Entity>
{
	private Models models;
	private Sprites sprites;
	private HudElements hud;

	public MixedObjects(View view)
	{
		this.models = new Models();
		this.sprites = new Sprites();
		this.hud = new HudElements();
	}

	@Override
	public void add(Entity model)
	{
		models.add(model);
	}

	public void add(Sprite sprite)
	{
		sprites.add(sprite);
	}

	public void addHud(Sprite sprite)
	{
		hud.add(sprite);
	}

	@Override
	public void dispose()
	{
		models.dispose();
		sprites.dispose();
		hud.dispose();
	}

	public MyEnvironment environment()
	{
		return models.environment();
	}

	@Override
	public boolean isEmpty()
	{
		return sprites.isEmpty()
			&& models.isEmpty()
			&& hud.isEmpty();
	}

	@Override
	public void pause()
	{
		models.pause();
		sprites.pause();
		hud.pause();
	}

	@Override
	public void remove(Entity model)
	{
		models.remove(model);
	}

	public void remove(Sprite sprite)
	{
		sprites.add(sprite);
	}

	public void removeHud(Sprite sprite)
	{
		hud.add(sprite);
	}

	@Override
	public void render(View view)
	{
		models.render(view);
		sprites.render(view);
		hud.render(view);
	}

	@Override
	public void resize(int width, int height)
	{
		models.resize(width, height);
		sprites.resize(width, height);
		hud.resize(width, height);
	}

	@Override
	public void resume()
	{
		models.resume();
		sprites.resume();
		hud.resume();
	}

	@Override
	public void update(float dt)
	{
		models.update(dt);
		sprites.update(dt);
		hud.update(dt);
	}
}
