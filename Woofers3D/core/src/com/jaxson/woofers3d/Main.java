package com.jaxson.woofers3d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jaxson.lib.util.MyMath;
import com.jaxson.woofers3d.states.*;
import com.jaxson.woofers3d.states.GameStateManager;
import com.jaxson.woofers3d.states.PlayState;

public class Main extends ApplicationAdapter
{
	public static final String TITLE        = "Woofers 3D";
	public static final int WIDTH           = 800;
	public static final int HEIGHT          = 600;
	public static final int FPS             = 300;
	public static final boolean VSYNC       = false;
	private static final float STEP         = 1f / 120f;
	private static final float CLAMP		= 1f / 4f;
	private static final double ASPECTRATIO = (float)(WIDTH) / (float)(HEIGHT);

	private float accumulator;
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
		float dt = Gdx.graphics.getDeltaTime();
		if (dt > CLAMP) dt = CLAMP;
		accumulator += dt;
		while (accumulator >= STEP)
		{
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
			gameStateManager.update(STEP);
			accumulator -= STEP;
		}
		gameStateManager.render(modelBatch);
	}

	@Override
	public void resize(int width, int height)
	{

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
