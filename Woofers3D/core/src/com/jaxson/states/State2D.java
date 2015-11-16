package com.jaxson.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State2D extends State
{
	protected OrthographicCamera camera;

	public State2D(GameStateManager gameStateManager)
	{
		super(gameStateManager);
		camera = new OrthographicCamera();
	}

	public abstract void render(SpriteBatch spriteBatch);
}
