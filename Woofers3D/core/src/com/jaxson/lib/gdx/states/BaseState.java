package com.jaxson.lib.gdx.states;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.jaxson.lib.gdx.backend.Game;
import com.jaxson.lib.gdx.backend.renderer.MixedRenderer;
import com.jaxson.lib.gdx.graphics.g2d.Sprite;
import com.jaxson.lib.gdx.graphics.g3d.entities.types.Entity;
import com.jaxson.lib.gdx.graphics.g3d.environment.MyEnvironment;
import com.jaxson.lib.gdx.graphics.views.View;
import com.jaxson.lib.gdx.util.GameObject;

public abstract class BaseState extends GameObject
{
	private Game game;
	private MixedRenderer renderer;

	protected BaseState(Game game)
	{
		this.game = game;
		this.renderer = new MixedRenderer(getView());
	}

	public void add(Entity entity)
	{
		renderer.add(entity);
	}

	public void add(Sprite sprite)
	{
		renderer.add(sprite);
	}

	public void addHud(Sprite sprite)
	{
		renderer.add(sprite);
	}

	@Override
	public void dispose()
	{
		renderer.dispose();
	}

	public MyEnvironment getEnvironment()
	{
		return renderer.getEnvironment();
	}

	public Game getGame()
	{
		return game;
	}

	public int getHeight()
	{
		return getGraphics().getHeight();
	}

	public View getView()
	{
		return getGame().getView();
	}

	public int getWidth()
	{
		return getGraphics().getWidth();
	}

	public void remove(Entity entity)
	{
		renderer.remove(entity);
	}

	public void remove(Sprite sprite)
	{
		renderer.remove(sprite);
	}

	public void removeHud(Sprite sprite)
	{
		renderer.removeHud(sprite);
	}

	@Override
	public void render(View view)
	{
		renderer.render(view);
	}

	@Override
	public void resize(int width, int height)
	{
		renderer.resize(width, height);
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		renderer.update(dt);
	}

	private Graphics getGraphics()
	{
		return getGame().getGraphics();
	}

	private Input getInput()
	{
		return getGame().getInput();
	}
}
