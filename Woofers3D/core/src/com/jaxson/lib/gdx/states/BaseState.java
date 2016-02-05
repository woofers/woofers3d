package com.jaxson.lib.gdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaxson.lib.gdx.backend.GameManager;
import com.jaxson.lib.gdx.backend.renderer.MixedRenderer;
import com.jaxson.lib.gdx.graphics.cameras.TargetCamera;
import com.jaxson.lib.gdx.graphics.g2d.GdxSprite;
import com.jaxson.lib.gdx.graphics.g3d.Entity;
import com.jaxson.lib.gdx.graphics.g3d.MyEnvironment;
import com.jaxson.lib.gdx.util.GameObject;

public abstract class BaseState extends GameObject
{
	private GameManager gameManager;
	private MixedRenderer renderer;

	protected BaseState(GameManager gameManager)
	{
		this.gameManager = gameManager;
		this.renderer = new MixedRenderer();
	}

	public void add(Entity entity)
	{
		renderer.add(entity);
	}

	public void add(GdxSprite sprite)
	{
		renderer.add(sprite);
	}

	@Override
	public void dispose()
	{
		renderer.dispose();
	}

	public Camera getCamera()
	{
		return gameManager.getCamera();
	}

	public MyEnvironment getEnvironment()
	{
		return renderer.getEnvironment();
	}

	private Graphics getGraphics()
	{
		return gameManager.getGraphics();
	}

	public int getHeight()
	{
		return getGraphics().getHeight();
	}

	private Input getInput()
	{
		return gameManager.getInput();
	}

	public TargetCamera getTargetCamera()
	{
		return gameManager.getTargetCamera();
	}

	public Viewport getViewport()
	{
		return gameManager.getViewport();
	}

	public int getWidth()
	{
		return getGraphics().getWidth();
	}

	public void remove(Entity entity)
	{
		renderer.remove(entity);
	}

	public void remove(GdxSprite sprite)
	{
		renderer.remove(sprite);
	}

	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch)
	{
		renderer.render(spriteBatch, modelBatch, getCamera());
	}

	public void resize(int width, int height)
	{

	}

	public void setCamera(Camera camera)
	{
		gameManager.setCamera(camera);
	}

	public void setViewport(Viewport viewport)
	{
		gameManager.setViewport(viewport);
	}

	@Override
	public void update(float dt)
	{
		super.update(dt);
		renderer.update(dt);
	}
}
