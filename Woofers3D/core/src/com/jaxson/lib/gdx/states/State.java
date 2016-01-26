package com.jaxson.lib.gdx.states;

import com.jaxson.lib.gdx.backend.GameManager;

public abstract class State extends SubState
{
	private SubState pauseState;

	public State(GameManager gameManager)
	{
		super(gameManager);
	}

	@Override
	public SubState getPauseState()
	{
		return pauseState;
	}

	@Override
	public boolean hasPauseState()
	{
		return getPauseState() != null;
	}

	@Override
	public void setPauseState(SubState pauseState)
	{
		this.pauseState = pauseState;
	}
}
