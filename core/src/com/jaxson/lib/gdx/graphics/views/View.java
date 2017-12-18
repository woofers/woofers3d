package com.jaxson.lib.gdx.graphics.views;

import java.util.HashMap;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;

public class View
{
	private Viewport sprite;
	private Viewport model;
	private Viewport hud;
	private HashMap<String, Viewport> extras;
	private SpriteBatch spriteBatch;
	private ModelBatch modelBatch;

	public View(int width, int height)
	{
		this(new ExtendViewport(width, height),
				new ExtendViewport(width,
						height,
						new TargetCamera(width, height)),
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
		setToOrthographic();
	}

	public Viewport add(Viewport viewport, String name)
	{
		if (viewport != null) extras.put(name, viewport);
		return viewport;
	}

	public void dispose()
	{
		spriteBatch().dispose();
		modelBatch().dispose();
	}

	public Viewport get(String name)
	{
		return extras.get(name);
	}

	public Viewport hudView()
	{
		return hud;
	}

	public ModelBatch modelBatch()
	{
		return modelBatch;
	}

	public Viewport modelView()
	{
		return model;
	}

	public Viewport remove(String name)
	{
		return extras.remove(name);
	}

	public Viewport remove(Viewport viewport)
	{
		return extras.remove(viewport);
	}

	public Viewport rename(String oldName, String newName)
	{
		if (oldName.equals(newName)) return get(oldName);
		Viewport viewport = remove(oldName);
		return add(viewport, newName);
	}

	public void resize(int width, int height)
	{
		spriteBatch().dispose();
		modelBatch().dispose();
		spriteBatch = new SpriteBatch();
		modelBatch = new ModelBatch();
		spriteView().update(width, height);
		modelView().update(width, height);
		hudView().update(width, height);
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

	public void setShaderProvider(ShaderProvider shaderProvider)
	{
		if (modelBatch != null)	modelBatch().dispose();
		this.modelBatch = new ModelBatch(shaderProvider);
	}

	private void setToOrthographic()
	{
		OrthographicCamera camera = validateCamera(spriteView().getCamera());
		camera.setToOrtho(false);
		spriteView().setCamera(camera);
	}

	public SpriteBatch spriteBatch()
	{
		return spriteBatch;
	}

	public Viewport spriteView()
	{
		return sprite;
	}

	public void update(float dt)
	{
		spriteView().getCamera().update();
		modelView().getCamera().update();
		hudView().getCamera().update();
		for (Viewport viewport: extras.values())
		{
			viewport.getCamera().update();
		}
	}

	private OrthographicCamera validateCamera(Camera camera)
	{
		if (camera instanceof OrthographicCamera)
		{
			return (OrthographicCamera) camera;
		}
		return new OrthographicCamera();
	}
}
