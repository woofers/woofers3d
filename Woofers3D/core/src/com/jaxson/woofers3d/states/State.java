package com.jaxson.woofers3d.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.jaxson.woofers3d.states.GameStateManager;

public abstract class State
{
	protected Vector3 mouse;
	protected GameStateManager gameStateManager;

	public State(GameStateManager gameStateManager)
	{
		this.gameStateManager = gameStateManager;
		this.mouse = new Vector3();
	}

	public abstract void dispose();

	protected abstract void handleInput();

	public void update(float dt)
	{
		handleInput();
	}
}
