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
	private static final double ASPECTRATIO = (float)(WIDTH) / (float)(HEIGHT);

	private GameStateManager gameStateManager;
	private ModelBatch modelBatch;
	private Rectangle viewport;

	@Override
	public void create()
	{
		gameStateManager = new GameStateManager();
		modelBatch = new ModelBatch();
		//viewport = new Rectangle(0, 0, WIDTH, HEIGHT);
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
		//Gdx.gl.glViewport((int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		gameStateManager.update(Gdx.graphics.getDeltaTime());
		gameStateManager.render(modelBatch);
	}

	@Override
	public void resize(int width, int height)
	{
		/*
		float aspectRatio = (float)width / (float)height;
		float scale = 1f;
		Vector2 crop = new Vector2(0f, 0f);

		if(aspectRatio > ASPECTRATIO)
		{
			scale = (float)(height) / (float)(HEIGHT);
			crop.x = (width - WIDTH * scale) / 2f;
		}
		else if(aspectRatio < ASPECTRATIO)
		{
			scale = (float)(width) / (float)(WIDTH);
			crop.y = (height - HEIGHT * scale) / 2f;
		}
		else
		{
			scale = (float)(width) / (float)(WIDTH);
		}

		int newWidth = MyMath.toInt(width * ASPECTRATIO * scale);
		int newHeight = MyMath.toInt(height * scale);
		viewport = new Rectangle(crop.x, crop.y, newWidth, newHeight);
		*/
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
