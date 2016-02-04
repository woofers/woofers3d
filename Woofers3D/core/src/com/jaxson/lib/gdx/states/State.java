package com.jaxson.lib.gdx.states;

import com.jaxson.lib.gdx.backend.GameManager;

public abstract class State extends BaseState
{
	private SubState subState;

	public State(GameManager gameManager)
	{
		super(gameManager);
	}

	public SubState getSubState()
	{
		return subState;
	}

	public boolean hasPauseState()
	{
		if (hasSubState()) return getSubState().isPauseState();
		return false;
	}

	public boolean hasSubState()
	{
		return getSubState() != null;
	}

	public void setSubState(SubState subState)
	{
		this.subState = subState;
	}
}
