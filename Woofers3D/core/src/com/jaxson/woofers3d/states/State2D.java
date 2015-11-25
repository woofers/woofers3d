package com.jaxson.woofers3d.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.jaxson.lib.util.MyArrayList;
import com.jaxson.woofers3d.sprites.Sprite;
import com.jaxson.woofers3d.states.GameStateManager;
import com.jaxson.woofers3d.states.State;

public abstract class State2D extends State
{
	protected OrthographicCamera camera;
	private MyArrayList<Sprite> sprites;

	public State2D(GameStateManager gameStateManager)
	{
		super(gameStateManager);
		camera = new OrthographicCamera();
	}

	public void add(Sprite sprite)
	{
		sprites.add(sprite);
	}

	public abstract void render(SpriteBatch spriteBatch);
}
