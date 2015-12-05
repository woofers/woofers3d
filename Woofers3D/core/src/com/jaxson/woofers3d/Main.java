package com.jaxson.woofers3d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.jaxson.lib.gdx.states.GameStateManager;
import com.jaxson.woofers3d.states.*;

public class Main extends ApplicationAdapter
{
	public static final String TITLE       = "Woofers 3D";
	public static final int WIDTH          = 1024;
	public static final int HEIGHT         = 768;
	public static final int FPS            = 300;
	public static final int BACKGROUND_FPS = -1;
	public static final boolean VSYNC      = false;

	private static final float STEP         = 1f / 120f;
	private static final float CLAMP        = 1f / 4f;
	private static final double ASPECTRATIO = (float)(WIDTH) / (float)(HEIGHT);
	private static final int CLEAR_MASK     = GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT;
	private static final Color CLEAR_COLOR  = new Color(0f, 0f, 1f, 1f);

	private float accumulator;
	private GameStateManager gameStateManager;
	private ModelBatch modelBatch;
	private SpriteBatch spriteBatch;

	@Override
	public void create()
	{
		gameStateManager = new GameStateManager();
		modelBatch = new ModelBatch();
		spriteBatch = new SpriteBatch();
		Gdx.gl.glClearColor(CLEAR_COLOR.r, CLEAR_COLOR.g, CLEAR_COLOR.b, CLEAR_COLOR.a);
		gameStateManager.push(new PlayState(gameStateManager));
	}

	@Override
	public void dispose()
	{
		spriteBatch.dispose();
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
			Gdx.gl.glClear(CLEAR_MASK);
			gameStateManager.update(STEP);
			accumulator -= STEP;
		}
		gameStateManager.render(spriteBatch, modelBatch);
	}

	@Override
	public void resize(int width, int height)
	{

	}

	@Override
	public void resume()
	{
		System.out.println("resume");
	}

	@Override
	public void pause()
	{
		System.out.println("paused");
	}

}
