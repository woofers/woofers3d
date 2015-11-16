package com.jaxson;

import com.jaxson.states.*;
import com.jaxson.util.MyMath;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;

public class Main extends ApplicationAdapter
{
	public static final String TITLE        = "Woofers 3D";
	public static final int WIDTH           = 800;
	public static final int HEIGHT          = 600;
	private static final double ASPECTRATIO = WIDTH / HEIGHT;

	private GameStateManager gameStateManager;
	private ModelBatch modelBatch;

	@Override
	public void create()
	{
		gameStateManager = new GameStateManager();
		modelBatch = new ModelBatch();
		gameStateManager.push(new PlayState(gameStateManager));
		Gdx.gl.glClearColor(0, 0, 0, 1);
	}

	@Override
	public void dispose()
	{
		modelBatch.dispose();
		gameStateManager.popAll();
	}

	@Override
	public void render()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		gameStateManager.update(Gdx.graphics.getDeltaTime());
		gameStateManager.render(modelBatch);
	}

	@Override
	public void resize(int width, int height)
	{
		Gdx.gl.glViewport(0, 0, width, MyMath.toInt(height * ASPECTRATIO));
		Gdx.graphics.setDisplayMode(width, MyMath.toInt(height * ASPECTRATIO), false);
	}

	@Override
	public void pause()
	{

	}

	@Override
	public void resume()
	{

	}
}
