package com.jaxson.lib.gdx.graphics.views;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.HashMap;
import java.util.Map;

public class View
{
	private Viewport sprite;
	private Viewport model;
	private Viewport hud;
	private Map<String, Viewport> extras;
	private SpriteBatch spriteBatch;
	private ModelBatch modelBatch;

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
		this.spriteBatch = new SpriteBatch();
		this.modelBatch = new ModelBatch();

		OrthographicCamera camera = null;
		if (getSpriteView().getCamera() instanceof OrthographicCamera) camera = (OrthographicCamera) getSpriteView().getCamera();
		else camera = new OrthographicCamera();
		camera.setToOrtho(false);
		getSpriteView().setCamera(camera);
	}

	public Viewport add(Viewport viewport, String name)
	{
		if (viewport != null) extras.put(name, viewport);
		return viewport;
	}

	public void dispose()
	{
		getSpriteBatch().dispose();
		getModelBatch().dispose();
	}

	public Viewport get(String name)
	{
		return extras.get(name);
	}

	public Viewport getHudView()
	{
		return hud;
	}

	public ModelBatch getModelBatch()
	{
		return modelBatch;
	}

	public Viewport getModelView()
	{
		return model;
	}

	public SpriteBatch getSpriteBatch()
	{
		return spriteBatch;
	}

	public Viewport getSpriteView()
	{
		return sprite;
	}

	public Viewport remove(String name)
	{
		return extras.remove(name);
	}

	public Viewport rename(String oldName, String newName)
	{
		if (oldName.equals(newName)) return get(oldName);
		Viewport viewport = remove(oldName);
		return add(viewport, newName);
	}

	public void resize(int width, int height)
	{
		getSpriteBatch().dispose();
		getModelBatch().dispose();
		spriteBatch = new SpriteBatch();
		modelBatch = new ModelBatch();
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
		//getModelView().getCamera().update();
		getHudView().getCamera().update();
		for (Viewport viewport: extras.values())
		{
			viewport.getCamera().update();
		}
	}
}
