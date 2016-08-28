package com.jaxson.lib.gdx.graphics.views;

import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.HashMap;
import java.util.Map;

public class View
{
	private Viewport sprite;
	private Viewport model;
	private Viewport hud;
	private Map<String, Viewport> extras;

	public View(int width, int height)
	{
		this(new ExtendViewport(width, height),
			new ExtendViewport(width, height, new TargetCamera(width, height)),
			new FitViewport(width, height));
	}

	public View(Viewport sprite, Viewport model, Viewport hud)
	{
		this.sprite = sprite;
		this.model = model;
		this.hud = hud;
		this.extras = new HashMap<>();
	}

	public void add(Viewport viewport, String name)
	{
		if (viewport != null) extras.put(name, viewport);
	}

	public Viewport get(String name)
	{
		return extras.get(name);
	}

	public Viewport getHudView()
	{
		return hud;
	}

	public Viewport getModelView()
	{
		return model;
	}

	public Viewport getSpriteView()
	{
		return sprite;
	}

	public void remove(String name)
	{
		extras.remove(name);
	}

	public void resize(int width, int height)
	{
		getSpriteView().update(width, height);
		getModelView().update(width, height);
		getHudView().update(width, height);
		for (Viewport viewport: extras.values())
		{
			viewport.update(width, height);
		}
	}

	public void setHudView(Viewport hud)
	{
		this.hud = hud;
	}

	public void setModelView(Viewport model)
	{
		this.model = model;
	}

	public void setSpriteView(Viewport sprite)
	{
		this.sprite = sprite;
	}

	public void update()
	{
		getSpriteView().getCamera().update();
		getModelView().getCamera().update();
		getHudView().getCamera().update();
		for (Viewport viewport: extras.values())
		{
			viewport.getCamera().update();
		}
	}
}
