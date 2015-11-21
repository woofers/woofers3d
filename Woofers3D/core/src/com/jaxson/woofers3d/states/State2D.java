package com.jaxson.woofers3d.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jaxson.woofers3d.states.GameStateManager;
import com.jaxson.woofers3d.states.State;

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
